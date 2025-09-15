package com.mingles.web.repository;

import com.mingles.web.entity.BookingAuditLogEntity;
import com.mingles.web.entity.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingAuditLogRepository extends JpaRepository<BookingAuditLogEntity, Long> {
    List<BookingAuditLogEntity> findByBookingOrderByCreatedAtAsc(BookingEntity booking);
}
