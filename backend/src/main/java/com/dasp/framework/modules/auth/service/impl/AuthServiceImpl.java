package com.dasp.framework.modules.auth.service.impl;

import com.dasp.framework.core.exception.UnauthorizedAccessException;
import com.dasp.framework.core.security.JwtTokenProvider;
import com.dasp.framework.core.security.RedisTokenBlacklistService;
import com.dasp.framework.modules.auth.dto.LoginRequest;
import com.dasp.framework.modules.auth.dto.LoginResponse;
import com.dasp.framework.modules.auth.dto.RefreshTokenRequest;
import com.dasp.framework.modules.auth.service.AuthService;
import com.dasp.framework.modules.user.domain.Permission;
import com.dasp.framework.modules.user.domain.Role;
import com.dasp.framework.modules.user.domain.User;
import com.dasp.framework.modules.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;
    private final RedisTokenBlacklistService blacklistService;

    @Override
    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmailAndIsDeletedFalse(request.getEmail())
                .orElseThrow(() -> new UnauthorizedAccessException("Invalid email or password"));

        if (!user.getIsActive()) {
            throw new UnauthorizedAccessException("Account is disabled. Please contact system administrator.");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new UnauthorizedAccessException("Invalid email or password");
        }

        List<String> permissions = extractPermissions(user);
        String accessToken = tokenProvider.generateAccessToken(user.getEmail(), permissions);
        String refreshToken = tokenProvider.generateRefreshToken(user.getEmail());

        return LoginResponse.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .permissions(permissions)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public LoginResponse refreshToken(RefreshTokenRequest request) {
        String token = request.getRefreshToken();
        if (!tokenProvider.validateToken(token)) {
            throw new UnauthorizedAccessException("Invalid refresh token");
        }

        String username = tokenProvider.getUsernameFromToken(token);
        User user = userRepository.findByEmailAndIsDeletedFalse(username)
                .orElseThrow(() -> new UnauthorizedAccessException("User not found"));

        List<String> permissions = extractPermissions(user);
        String newAccessToken = tokenProvider.generateAccessToken(user.getEmail(), permissions);
        String newRefreshToken = tokenProvider.generateRefreshToken(user.getEmail());

        return LoginResponse.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .tokenType("Bearer")
                .permissions(permissions)
                .build();
    }

    @Override
    public void logout(String authorizationHeader) {
        if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
            String jwt = authorizationHeader.substring(7);
            if (tokenProvider.validateToken(jwt)) {
                String tokenId = tokenProvider.getTokenId(jwt);
                long remainingExpirationMs = tokenProvider.getRemainingExpirationMs(jwt);
                blacklistService.blacklistToken(tokenId, remainingExpirationMs);
            }
        }
    }

    private List<String> extractPermissions(User user) {
        List<String> permissions = new ArrayList<>();
        for (Role role : user.getRoles()) {
            for (Permission perm : role.getPermissions()) {
                if (!permissions.contains(perm.getName())) {
                    permissions.add(perm.getName());
                }
            }
        }
        return permissions;
    }
}
