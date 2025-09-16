package com.mingles.web.dto.auth;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationResponse {
    String accessToken;
    String refreshToken;
}