package com.bookmyshow.theatre.dto;

import lombok.Data;

@Data
public class TheatreDto {

    private Long cityId;
    private String name;
    private String address;
    private Long theatreId;
    private String status;

}
