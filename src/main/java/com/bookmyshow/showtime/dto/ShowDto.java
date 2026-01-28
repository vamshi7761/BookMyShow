package com.bookmyshow.showtime.dto;

import com.bookmyshow.moviecatalog.enums.Language;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ShowDto {
    private Long showId;       // for get/update/delete
    private Long movieId;      // required on create
    private Long theatreId;    // required on create
    private Long screenId;     // required on create
    private Date startTime;    // required on create
    private Language language; // required on create
}
