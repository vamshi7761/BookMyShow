
package com.bookmyshow.showtime.service;

import com.bookmyshow.main.dto.InputCommand;
import com.bookmyshow.main.dto.OutputCommand;

public interface ShowSeatService {
    OutputCommand generateShowSeatsForShow(InputCommand request);
    OutputCommand getShowSeats(InputCommand request);
    OutputCommand getAvailableShowSeats(InputCommand request);
}
