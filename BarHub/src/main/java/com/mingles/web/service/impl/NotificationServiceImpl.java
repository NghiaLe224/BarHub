package com.mingles.web.service.impl;

import com.mingles.web.dto.booking.BookingResponse;
import com.mingles.web.service.NotificationService;
import com.mingles.web.websocket.BookingTopics;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public void notifyStaffNewBooking(BookingResponse booking) {
        simpMessagingTemplate.convertAndSend(BookingTopics.STAFF_NEW_BOOKING, booking);
    }

    @Override
    public void notifyUserBookingUpdated(Long bookingId, BookingResponse booking) {
        simpMessagingTemplate.convertAndSend(BookingTopics.userBookingTopic(bookingId), booking);
    }
}
