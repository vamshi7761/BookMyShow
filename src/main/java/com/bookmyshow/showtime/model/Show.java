package com.bookmyshow.showtime.model;

import com.bookmyshow.main.model.BaseModel;
import com.bookmyshow.moviecatalog.enums.Language;
import com.bookmyshow.moviecatalog.model.Movie;
import com.bookmyshow.theatre.model.Screen;
import com.bookmyshow.theatre.model.Theatre;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "shows")
public class Show extends BaseModel {

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "screen_id", nullable = false)
    private Screen screen;

    @ManyToOne
    @JoinColumn(name = "theatre_id", nullable = false)
    private Theatre theatre;

    @OneToMany(mappedBy = "show")
    private List<ShowSeat> showSeatList;

    @OneToMany(mappedBy =  "show")
    private List<ShowSeatType> showSeatTypes;

    @Column(name = "start_time", nullable = false)
    private Date startTime;

    @Column(name = "language", nullable = false)
    private Language language;
}
