package com.mingles.web.listener;

import com.mingles.web.entity.BookingAuditLogEntity;
import com.mingles.web.entity.BookingEntity;
import com.mingles.web.repository.BookingAuditLogRepository;
import jakarta.persistence.PostUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

@Component
@ApplicationScope
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class BookingStatusListener {

    private final BookingAuditLogRepository logRepo;

    @PostUpdate
    public void onBookingUpdated(BookingEntity booking) {
        if (booking.getPreviousStatus() != null &&
                booking.getStatus() != booking.getPreviousStatus()) {

            String actor = "unknown";
            try {
                var auth = SecurityContextHolder.getContext().getAuthentication();
                if (auth != null && auth.isAuthenticated()) {
                    actor = auth.getName();
                }
            } catch (Exception ignored) {}

            BookingAuditLogEntity log = BookingAuditLogEntity.builder()
                    .bookingId(booking.getId())
                    .oldStatus(booking.getPreviousStatus().name())
                    .newStatus(booking.getStatus().name())
                    .actor(actor)
                    .build();

            logRepo.save(log);
        }
    }
}
