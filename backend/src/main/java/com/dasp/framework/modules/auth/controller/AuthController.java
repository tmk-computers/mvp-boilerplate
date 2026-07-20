package com.dasp.framework.modules.auth.controller;

import com.dasp.framework.core.base.BaseResponse;
import com.dasp.framework.modules.auth.dto.LoginRequest;
import com.dasp.framework.modules.auth.dto.LoginResponse;
import com.dasp.framework.modules.auth.dto.RefreshTokenRequest;
import com.dasp.framework.modules.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<BaseResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(BaseResponse.success(response, "Login successful"));
    }

    @PostMapping("/refresh")
    public ResponseEntity<BaseResponse<LoginResponse>> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        LoginResponse response = authService.refreshToken(request);
        return ResponseEntity.ok(BaseResponse.success(response, "Token refreshed successfully"));
    }

    @PostMapping("/logout")
    public ResponseEntity<BaseResponse<Void>> logout(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        authService.logout(authHeader);
        return ResponseEntity.ok(BaseResponse.success(null, "Logout successful"));
    }
}
