package com.mingles.web.service.impl;

import com.mingles.web.dto.booking.BookingLogResponse;
import com.mingles.web.dto.booking.BookingRequest;
import com.mingles.web.dto.booking.BookingResponse;
import com.mingles.web.dto.booking.UpdateBookingStatusRequest;
import com.mingles.web.dto.common.ApiResponse;
import com.mingles.web.entity.BookingAuditLogEntity;
import com.mingles.web.entity.BookingEntity;
import com.mingles.web.entity.BookingStatus;
import com.mingles.web.entity.UserEntity;
import com.mingles.web.repository.BookingAuditLogRepository;
import com.mingles.web.repository.BookingRepository;
import com.mingles.web.repository.UserRepository;
import com.mingles.web.service.BookingService;
import com.mingles.web.service.EmailService;
import com.mingles.web.service.NotificationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final BookingAuditLogRepository auditLogRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;
    private final EmailService emailService;
    private final ModelMapper mapper;

    @Override
    @Transactional
    public ApiResponse<BookingResponse> createBooking(BookingRequest request) {
        UserEntity currentUser = getCurrentUserOrThrow();
        BookingEntity entity = mapper.map(request, BookingEntity.class);
        entity.setStatus(BookingStatus.PENDING);
        entity.setUser(currentUser);
        BookingEntity saved = bookingRepository.save(entity);

        BookingResponse response = mapper.map(saved, BookingResponse.class);

        notificationService.notifyStaffNewBooking(response);
        return ApiResponse.created(response);
    }

    @Override
    @Transactional
    public ApiResponse<BookingResponse> confirmBooking(Long bookingId, UpdateBookingStatusRequest request) {
        BookingEntity booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found: " + bookingId));
        BookingStatus oldStatus = booking.getStatus();
        BookingStatus newStatus = request.getStatus();
        if (oldStatus != BookingStatus.PENDING) {
            throw new IllegalStateException("Only PENDING bookings can be updated");
        }
        booking.setStatus(newStatus);
        BookingEntity saved = bookingRepository.save(booking);

        BookingAuditLogEntity log = BookingAuditLogEntity.builder()
                .booking(saved)
                .oldStatus(oldStatus)
                .newStatus(newStatus)
                .changedBy(currentUsername())
                .reason(request.getReason())
                .build();
        auditLogRepository.save(log);

        BookingResponse response = mapper.map(saved, BookingResponse.class);

        notificationService.notifyUserBookingUpdated(saved.getId(), response);
        if (newStatus == BookingStatus.CONFIRMED) emailService.sendBookingConfirmed(saved);
        else if (newStatus == BookingStatus.REJECTED) emailService.sendBookingRejected(saved, request.getReason());
        return ApiResponse.success(response);
    }

    @Override
    @Transactional
    public List<BookingLogResponse> getAuditLogs(Long bookingId) {
        BookingEntity booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found: " + bookingId));
        return auditLogRepository.findByBookingOrderByCreatedAtAsc(booking)
                .stream().map(log -> mapper.map(log, BookingLogResponse.class)).toList();
    }

    private String currentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (auth != null ? auth.getName() : null);
    }

    private UserEntity getCurrentUserOrThrow() {
        String username = currentUsername();
        if (username == null || username.isBlank()) {
            throw new AccessDeniedException("Unauthenticated");
        }
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalStateException("User not found by username: " + username));
    }
}
