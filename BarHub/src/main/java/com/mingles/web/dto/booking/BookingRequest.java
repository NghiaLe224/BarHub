package com.mingles.web.dto.booking;

import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public class BookingRequest {
    @NotBlank
    private String customerName;

    @NotBlank
    private String customerPhone;

    @Email @NotBlank
    private String customerEmail;

    @NotNull
    private LocalDateTime bookingTime;

    @NotNull @Min(1) @Max(50)
    private Integer numberOfGuests;

    private String note;
}
