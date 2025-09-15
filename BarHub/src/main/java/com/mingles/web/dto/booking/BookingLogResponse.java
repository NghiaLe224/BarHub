package com.mingles.web.dto.booking;

import com.mingles.web.entity.BookingStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingLogResponse {
    private BookingStatus oldStatus;
    private BookingStatus newStatus;
    private String changedBy;
    private String reason;
    private LocalDateTime createdAt;
}
