package com.bookmyshow.theatre.service;

import com.bookmyshow.main.dto.InputCommand;
import com.bookmyshow.main.dto.OutputCommand;

public interface SeatService {

    public OutputCommand addSeatsToScreen(InputCommand inputCommand);
    public OutputCommand getSeatsByScreenId(InputCommand inputCommand);
    public OutputCommand updateSeatsInScreen(InputCommand inputCommand);
    public OutputCommand deleteSeatsFromScreen(InputCommand inputCommand);

}
