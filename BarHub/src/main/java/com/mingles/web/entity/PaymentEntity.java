package com.mingles.web.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentEntity extends BaseEntity {
    @Column(precision = 15, scale = 2)
    private double amount;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private PaymentMethod method;

    @Column
    private String transactionId;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    private BookingEntity bookingEntity;

    public enum Status {
        PENDING,
        SUCCESS,
        FAILED
    }

    public enum PaymentMethod {
        MONO,
        VNPAY,
        PAYPAL
    }
}
