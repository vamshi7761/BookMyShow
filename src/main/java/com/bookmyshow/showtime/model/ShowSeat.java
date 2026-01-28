package com.bookmyshow.showtime.model;

import com.bookmyshow.main.model.BaseModel;
import com.bookmyshow.theatre.model.Seat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.bookmyshow.showtime.enums.ShowSeatStatus;

import java.util.Date;

@Getter
@Setter
@Entity
public class ShowSeat extends BaseModel {
    // ShowSeat : Show
    @ManyToOne
    @JoinColumn(name = "show_id", nullable = false)
    private Show show;
    // ShowSeat M:1 Seat

    @ManyToOne
    @JoinColumn(nullable = false)
    private Seat seat;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status", nullable = false)
    private ShowSeatStatus status;

    @Column(name = "blocked_at")
    private Date blockedAt;

    public void setStatus(ShowSeatStatus status) {
        this.status = status;
        if(status.equals(ShowSeatStatus.BLOCKED)){
            setBlockedAt(new Date());
        }
    }
}

