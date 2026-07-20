package com.dasp.framework;

import com.dasp.framework.core.exception.UnauthorizedAccessException;
import com.dasp.framework.core.security.JwtTokenProvider;
import com.dasp.framework.core.security.RedisTokenBlacklistService;
import com.dasp.framework.modules.auth.dto.LoginRequest;
import com.dasp.framework.modules.auth.dto.LoginResponse;
import com.dasp.framework.modules.auth.service.impl.AuthServiceImpl;
import com.dasp.framework.modules.user.domain.User;
import com.dasp.framework.modules.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenProvider tokenProvider;

    @Mock
    private RedisTokenBlacklistService blacklistService;

    @InjectMocks
    private AuthServiceImpl authService;

    private User sampleUser;

    @BeforeEach
    void setUp() {
        sampleUser = new User();
        sampleUser.setId(UUID.randomUUID());
        sampleUser.setEmail("admin@dasp.com");
        sampleUser.setPasswordHash("hashed_password");
        sampleUser.setFirstName("Admin");
        sampleUser.setLastName("User");
        sampleUser.setIsActive(true);
        sampleUser.setRoles(Set.of());
    }

    @Test
    void login_Successful_ShouldReturnLoginResponse() {
        LoginRequest request = new LoginRequest();
        request.setEmail("admin@dasp.com");
        request.setPassword("Admin@123");

        when(userRepository.findByEmailAndIsDeletedFalse("admin@dasp.com")).thenReturn(Optional.of(sampleUser));
        when(passwordEncoder.matches("Admin@123", "hashed_password")).thenReturn(true);
        when(tokenProvider.generateAccessToken(anyString(), anyList())).thenReturn("mock_access_token");
        when(tokenProvider.generateRefreshToken(anyString())).thenReturn("mock_refresh_token");

        LoginResponse response = authService.login(request);

        assertNotNull(response);
        assertEquals("admin@dasp.com", response.getEmail());
        assertEquals("mock_access_token", response.getAccessToken());
        assertEquals("mock_refresh_token", response.getRefreshToken());
    }

    @Test
    void login_InvalidPassword_ShouldThrowUnauthorizedException() {
        LoginRequest request = new LoginRequest();
        request.setEmail("admin@dasp.com");
        request.setPassword("WrongPassword");

        when(userRepository.findByEmailAndIsDeletedFalse("admin@dasp.com")).thenReturn(Optional.of(sampleUser));
        when(passwordEncoder.matches("WrongPassword", "hashed_password")).thenReturn(false);

        assertThrows(UnauthorizedAccessException.class, () -> authService.login(request));
    }
}
