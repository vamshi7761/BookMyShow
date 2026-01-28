package com.bookmyshow.booking.model;

import com.bookmyshow.main.model.BaseModel;
import com.bookmyshow.payment.model.Payment;
import com.bookmyshow.showtime.model.Show;
import com.bookmyshow.showtime.model.ShowSeat;
import com.bookmyshow.user.model.User;
import jakarta.persistence.*;

import java.util.List;
import com.bookmyshow.booking.enums.BookingStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Builder
public class Booking extends BaseModel {

    @OneToMany(mappedBy = "booking")
    private List<Payment> payments;

    @OneToOne
    @JoinColumn(name = "successful_payment_id", unique = true)
    private Payment successfulPayment;

    @Column(nullable = false)
    private Double amount;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "show_id", nullable = false)
    private Show show;

    @OneToMany
    private List<ShowSeat> showSeats;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private BookingStatus bookingStatus;
}
