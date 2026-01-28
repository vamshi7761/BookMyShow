
package com.bookmyshow.showtime.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShowSeatTypeDto {
    private Long showSeatTypeId; // for GET/UPDATE/DELETE
    private Long showId;         // required on CREATE (optional on UPDATE if changing)
    private Long seatTypeId;     // required on CREATE (optional on UPDATE if changing)
    private Double price;        // required on CREATE (optional on UPDATE if changing)
}
