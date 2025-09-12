package com.mingles.web.entity;

import com.mingles.web.listener.BookingStatusListener;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(BookingStatusListener.class)
public class BookingEntity extends BaseEntity{
    private String customerName;
    private String customerPhone;
    private LocalDateTime bookingTime;
    private int numberOfGuests;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "table_id")
    private TableEntity table;

    public enum Status {
        PENDING, CONFIRMED, REJECTED, CANCELLED
    }

    @Transient
    private Status previousStatus;

    @PostLoad
    public void recordPreviousStatus() {
        this.previousStatus = this.status;
    }
}
