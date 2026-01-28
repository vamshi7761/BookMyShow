package com.bookmyshow.theatre.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SeatDtoList {

    private Long screenId;

    private List<SeatDto> seats;

}
