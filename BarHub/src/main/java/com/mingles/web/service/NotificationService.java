package com.mingles.web.service;

import com.mingles.web.dto.booking.BookingResponse;

public interface NotificationService {
    void notifyStaffNewBooking(BookingResponse booking);
    void notifyUserBookingUpdated(Long bookingId, BookingResponse booking);
}
