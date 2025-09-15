package com.mingles.web.service;

import com.mingles.web.dto.booking.BookingLogResponse;
import com.mingles.web.dto.booking.BookingRequest;
import com.mingles.web.dto.booking.BookingResponse;
import com.mingles.web.dto.booking.UpdateBookingStatusRequest;
import com.mingles.web.dto.common.ApiResponse;
import jakarta.validation.Valid;

import java.util.List;

public interface BookingService {
    ApiResponse<BookingResponse> createBooking(BookingRequest request);
    ApiResponse<BookingResponse> confirmBooking(Long bookingId, UpdateBookingStatusRequest request);
    List<BookingLogResponse> getAuditLogs(Long bookingId);
}
