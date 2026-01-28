package com.bookmyshow.showtime.service;

import com.bookmyshow.main.dto.InputCommand;
import com.bookmyshow.main.dto.OutputCommand;

public interface ShowService {
    OutputCommand createShow(InputCommand request);
    OutputCommand updateShow(InputCommand request);
    OutputCommand deleteShow(InputCommand request);
    OutputCommand getShow(InputCommand request);
}
