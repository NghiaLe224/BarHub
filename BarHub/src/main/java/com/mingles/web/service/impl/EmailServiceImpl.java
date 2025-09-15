package com.mingles.web.service.impl;

import com.mingles.web.entity.BookingEntity;
import com.mingles.web.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Override
    public void sendBookingConfirmed(BookingEntity booking) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(booking.getCustomerEmail());
        message.setSubject("[Mingles] Booking Confirmed – #" + booking.getId());
        message.setText("Xin chào " + booking.getCustomerName() + ",\n\n" +
                "Booking của bạn vào " + booking.getBookingTime() + " cho " + booking.getNumberOfGuests() + " người đã được XÁC NHẬN.\n" +
                "Hẹn gặp bạn tại Mingles!\n\n" +
                "Trân trọng,");
        mailSender.send(message);
    }

    @Override
    public void sendBookingRejected(BookingEntity booking, String reason) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(booking.getCustomerEmail());
        message.setSubject("[Mingles] Booking Rejected – #" + booking.getId());
        message.setText("Xin chào " + booking.getCustomerName() + ",\n\n" +
                "Rất tiếc booking của bạn vào " + booking.getBookingTime() + " chưa thể xác nhận.\n" +
                (reason != null && !reason.isBlank() ? ("Lý do: " + reason + "\n") : "") +
                "Vui lòng thử khung giờ khác hoặc liên hệ CSKH.\n\n" +
                "Trân trọng,");
        mailSender.send(message);
    }
}
