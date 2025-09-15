package com.mingles.web.dto.booking;

import com.mingles.web.entity.BookingStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateBookingStatusRequest {
    @NotNull
    private BookingStatus status;
    private String reason;
}
