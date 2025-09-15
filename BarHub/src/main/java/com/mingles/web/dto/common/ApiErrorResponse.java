package com.mingles.web.dto.common;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiErrorResponse {
    private int status;
    private String error;
    private String message;
    private LocalDateTime timestamp;
}
