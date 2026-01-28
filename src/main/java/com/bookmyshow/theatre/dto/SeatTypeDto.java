package com.bookmyshow.theatre.dto;

import lombok.*;

@Getter
@Setter
@Builder
public class SeatTypeDto {
    private Long seatTypeId;
    private String type;
}