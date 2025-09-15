package com.mingles.web.repository;

import com.mingles.web.entity.BookingAuditLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingAuditLogRepository extends JpaRepository<BookingAuditLogEntity, Long> {
}
