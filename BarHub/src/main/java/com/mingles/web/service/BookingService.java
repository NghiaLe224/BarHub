package com.mingles.web.service;

import com.mingles.web.dto.booking.BookingRequest;
import com.mingles.web.dto.booking.BookingResponse;
import com.mingles.web.dto.common.ApiResponse;

public interface BookingService {
    ApiResponse<BookingResponse> createBooking(BookingRequest request);
}
