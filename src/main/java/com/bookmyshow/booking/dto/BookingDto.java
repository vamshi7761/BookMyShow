
package com.bookmyshow.booking.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookingDto {
    private Long userId;             // required
    private Long showId;             // required
    private List<Long> showSeatIds;  // required
    private List<Long> foodItemIds;  // optional, for future
}
