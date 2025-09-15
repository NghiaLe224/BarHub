package com.mingles.web.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "booking_audit_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class BookingAuditLogEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "booking_id", nullable = false)
    private BookingEntity booking;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus oldStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus newStatus;

    @Column(nullable = false)
    private String changedBy;

    private String reason;
}
