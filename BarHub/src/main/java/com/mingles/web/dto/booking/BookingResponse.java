package com.mingles.web.dto.booking;

import com.mingles.web.entity.BookingStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingResponse {
    private String customerName;
    private String customerPhone;
    private String customerEmail;
    private LocalDateTime bookingTime;
    private Integer numberOfGuests;
    private BookingStatus status;
    private String note;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
