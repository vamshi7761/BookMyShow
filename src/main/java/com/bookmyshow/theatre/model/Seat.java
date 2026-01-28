package com.bookmyshow.theatre.model;

import com.bookmyshow.main.model.BaseModel;
import com.bookmyshow.showtime.model.ShowSeat;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Seat extends BaseModel {

    @ManyToOne
    @JoinColumn(nullable = false)
    private Screen screenId;

    private String rowId;

    private Integer columnId;

    @ManyToOne
    @JoinColumn(nullable = false)
    private SeatType seatType;

    @OneToMany(mappedBy = "seat", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ShowSeat> showSeats;

}