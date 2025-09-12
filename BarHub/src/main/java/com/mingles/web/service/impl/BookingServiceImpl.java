package com.mingles.web.service.impl;

import com.mingles.web.dto.booking.BookingRequest;
import com.mingles.web.dto.booking.BookingResponse;
import com.mingles.web.dto.common.ApiResponse;
import com.mingles.web.service.BookingService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class BookingServiceImpl implements BookingService {
    @Override
    public ApiResponse<BookingResponse> createBooking(BookingRequest request) {
        return null;
    }
}
