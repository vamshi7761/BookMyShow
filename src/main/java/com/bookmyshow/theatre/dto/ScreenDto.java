package com.bookmyshow.theatre.dto;

import com.bookmyshow.theatre.enums.Feature;
import com.bookmyshow.theatre.enums.TheatreStatus;
import com.bookmyshow.theatre.model.Seat;
import com.bookmyshow.theatre.model.Theatre;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ScreenDto {

    private Long screenId;
    private String name;

    private List<String> features;

    private Long theatreId;

    private String status;
}
