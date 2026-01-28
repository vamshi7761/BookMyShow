package com.bookmyshow.theatre.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SeatDto {

    private Long seatId;
    private String rowId;
    private Integer columnId;
    private String seatTypeName;
    private String newRowId;
    private Integer newColumnId;

}
