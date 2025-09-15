package com.mingles.web.websocket;

public class BookingTopics {

    // Staff/Admin lắng nghe booking mới
    public static final String STAFF_NEW_BOOKING = "/topic/booking/staff";

    // User lắng nghe kết quả xử lý booking của chính mình (subscribe theo bookingId)
    public static String userBookingTopic(Long bookingId) {
        return "/topic/booking/user/" + bookingId;
    }
}
