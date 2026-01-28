
package com.bookmyshow.showtime.service;

import com.bookmyshow.main.dto.InputCommand;
import com.bookmyshow.main.dto.OutputCommand;

public interface ShowSeatTypeService {
    OutputCommand createShowSeatType(InputCommand request);
    OutputCommand updateShowSeatType(InputCommand request);
    OutputCommand deleteShowSeatType(InputCommand request);
    OutputCommand getShowSeatType(InputCommand request);

    // Optional utility
    OutputCommand listSeatTypesForShow(InputCommand request);
}
