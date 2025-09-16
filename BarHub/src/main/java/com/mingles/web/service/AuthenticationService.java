package com.mingles.web.service;

import com.mingles.web.dto.auth.AuthenticationRequest;
import com.mingles.web.dto.auth.AuthenticationResponse;
import com.mingles.web.dto.auth.UserCreationRequest;
import com.mingles.web.dto.common.ApiResponse;

public interface AuthenticationService {
    ApiResponse<?> registerAccount(UserCreationRequest userCreationRequest);
    ApiResponse<AuthenticationResponse> authenticate(AuthenticationRequest request);
}
