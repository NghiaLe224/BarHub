package com.mingles.web.service.impl;

import com.mingles.web.dto.auth.AuthenticationRequest;
import com.mingles.web.dto.auth.AuthenticationResponse;
import com.mingles.web.dto.auth.UserCreationRequest;
import com.mingles.web.dto.common.ApiResponse;
import com.mingles.web.entity.RefreshTokenEntity;
import com.mingles.web.entity.RoleEntity;
import com.mingles.web.entity.UserEntity;
import com.mingles.web.exception.MyRuntimeException;
import com.mingles.web.repository.RefreshTokenRepository;
import com.mingles.web.repository.RoleRepository;
import com.mingles.web.repository.UserRepository;
import com.mingles.web.service.AuthenticationService;
import com.mingles.web.util.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthenticationServiceImp implements AuthenticationService {
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final RefreshTokenRepository refreshTokenRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public ApiResponse<AuthenticationResponse> authenticate(AuthenticationRequest request) {
        var user = userRepository.findByUsername(request.getUsername()).orElseThrow(
                () -> new MyRuntimeException("Invalid username")
        );

        if (!passwordEncoder.matches(request.getPassword(), user.getHashingPassword())) {
            throw new MyRuntimeException("Invalid password");
        }

        Set<RoleEntity.RoleName> roleNames = user.getRoleEntities().stream()
                .map(RoleEntity::getRoleName)
                .collect(Collectors.toSet());
        String accessToken = JwtUtil.generateToken(user.getUsername(), roleNames);
        String refreshToken = JwtUtil.generateRefreshToken(user.getUsername());
        // Save refresh token to database
        RefreshTokenEntity refreshTokenEntity = new RefreshTokenEntity();
        refreshTokenEntity.setUserEntity(user);
        refreshTokenEntity.setTokenValue(refreshToken);
        refreshTokenEntity.setExpireTime(JwtUtil.extractExpiration(refreshToken));
        refreshTokenRepository.save(refreshTokenEntity);

        AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

        return new ApiResponse<>(authenticationResponse, "User Login successfully", LocalDateTime.now());
    }

    @Override
    public ApiResponse<?> registerAccount(UserCreationRequest userCreationRequest) {
        if(userRepository.existsByUsername(userCreationRequest.getUsername())) {
            throw new MyRuntimeException(userCreationRequest.getUsername() + " - username is already taken");
        }

        try {
            UserEntity newUser = new UserEntity();
            newUser.setUsername(userCreationRequest.getUsername());
            newUser.setEmail(userCreationRequest.getEmail());
            newUser.setHashingPassword(passwordEncoder.encode(userCreationRequest.getPassword()));
            // set USER role by default
            RoleEntity customerRole = roleRepository.findByRoleName(RoleEntity.RoleName.CUSTOMER)
                    .orElseThrow(() -> new MyRuntimeException("CUSTOMER Role not set in the database."));
            newUser.getRoleEntities().add(customerRole);
            userRepository.save(newUser);
            return new ApiResponse<>(null, "User registered successfully with username" , LocalDateTime.now());
        }catch (Exception e) {
            throw new MyRuntimeException("Error can't save user: " + e.getMessage());
        }

    }
}
