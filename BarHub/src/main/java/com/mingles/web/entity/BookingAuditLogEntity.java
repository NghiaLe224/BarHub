package com.mingles.web.entity;

import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingAuditLogEntity extends BaseEntity{
    private Long bookingId;
    private String oldStatus;
    private String newStatus;
    private String actor;
}
