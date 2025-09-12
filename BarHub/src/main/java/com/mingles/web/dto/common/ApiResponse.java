package com.mingles.web.dto.common;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse <T>{
    private T data;
    private String message;
    private LocalDateTime  timestamp;

}
