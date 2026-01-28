package com.bookmyshow.payment.model;

import com.bookmyshow.booking.model.Booking;
import com.bookmyshow.main.model.BaseModel;
import jakarta.persistence.*;
import com.bookmyshow.payment.enums.PaymentType;
import com.bookmyshow.payment.enums.PaymentStatus;

@Entity
public class Payment extends BaseModel {

    @Column(nullable = false, unique = true)
    private String reference;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private PaymentType paymentType;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private PaymentStatus paymentStatus;

    @ManyToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;
}
