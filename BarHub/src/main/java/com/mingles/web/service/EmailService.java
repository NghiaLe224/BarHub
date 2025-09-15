package com.mingles.web.service;

import com.mingles.web.entity.BookingEntity;

public interface EmailService {
    void sendBookingConfirmed(BookingEntity booking);
    void sendBookingRejected(BookingEntity booking, String reason);
}
