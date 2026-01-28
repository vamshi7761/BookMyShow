package com.bookmyshow.showtime.model;

import com.bookmyshow.main.model.BaseModel;
import com.bookmyshow.theatre.model.SeatType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ShowSeatType extends BaseModel {
    @ManyToOne
    private Show show;
    @ManyToOne
    private SeatType seatType;
    private double price;
}