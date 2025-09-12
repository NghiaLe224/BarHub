package com.mingles.web.dto.menu;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateMenuItemRequest {
    @NotEmpty(message = "The name of item cannot be empty")
    private String name;
    private String description;
    @NotNull(message = "The price of item cannot be null")
    private BigDecimal price;
    private MultipartFile image;
    private boolean isAvailable = true;
}
