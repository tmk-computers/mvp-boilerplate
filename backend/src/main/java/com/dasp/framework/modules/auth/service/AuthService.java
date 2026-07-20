package com.dasp.framework.modules.auth.service;

import com.dasp.framework.modules.auth.dto.LoginRequest;
import com.dasp.framework.modules.auth.dto.LoginResponse;
import com.dasp.framework.modules.auth.dto.RefreshTokenRequest;

public interface AuthService {

    LoginResponse login(LoginRequest request);

    LoginResponse refreshToken(RefreshTokenRequest request);

    void logout(String authorizationHeader);
}
