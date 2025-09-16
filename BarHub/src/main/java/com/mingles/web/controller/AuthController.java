package com.mingles.web.controller;
//Register
//Login
//Logout
//Forgot Password (gửi email/SMS)
//Reset Password (token)
// refresh token

import com.mingles.web.dto.auth.AuthenticationRequest;
import com.mingles.web.dto.auth.AuthenticationResponse;
import com.mingles.web.dto.auth.UserCreationRequest;
import com.mingles.web.dto.common.ApiResponse;
import com.mingles.web.service.AuthenticationService;
import com.mingles.web.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationService authenticationService;

    private final RefreshTokenService refreshTokenService;

    @GetMapping
    public ResponseEntity<?> hello(){
        return new ResponseEntity<>("Hello from AuthController", HttpStatus.OK);
    }

    @PostMapping("/hello")
    public ResponseEntity<?> helloPost(){
        return new ResponseEntity<>("Hello from AuthController", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthenticationResponse>> login(@RequestBody AuthenticationRequest request){

        return new ResponseEntity<>(authenticationService.authenticate(request), HttpStatus.FOUND);
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<?>> register(@RequestBody UserCreationRequest userCreationRequest){
        var res = authenticationService.registerAccount(userCreationRequest);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }


//    @PostMapping("/forgot-password")
//    public ResponseEntity<ApiResponse<Void>> forgotPassword(@RequestBody @Valid ForgotPasswordRequest request, BindingResult result) {
//        if (result.hasErrors()) {
//            return new ResponseEntity<>(ApiResponse.error("Invalid email"), HttpStatus.BAD_REQUEST);
//        }
//        ApiResponse<Void> res = authenticationService.forgotPassword(request);
//        return new ResponseEntity<>(res, HttpStatus.OK);
//    }
//
//    @PostMapping("/logout")
//    public ResponseEntity<ApiResponse<Void>> logout(@RequestBody String userId) {
//        ApiResponse<Void> res = authenticationService.logout(userId);
//        return new ResponseEntity<>(res, HttpStatus.OK);
//    }
//
//    @PostMapping("/reset-password")
//    public ResponseEntity<ApiResponse<Void>> resetPassword(@RequestBody @Valid ResetPasswordRequest request, BindingResult result) {
//        if (result.hasErrors()) {
//            return new ResponseEntity<>(ApiResponse.error("Invalid request"), HttpStatus.BAD_REQUEST);
//        }
//        ApiResponse<Void> res = authenticationService.resetPassword(request);
//        return new ResponseEntity<>(res, HttpStatus.OK);
//    }

}
