package com.bookmyshow.theatre.service;

import com.bookmyshow.main.dto.InputCommand;
import com.bookmyshow.main.dto.OutputCommand;

public interface ScreenService {

    public OutputCommand createScreen(InputCommand request);
    public OutputCommand updateScreen(InputCommand request);
    public OutputCommand deleteScreen(InputCommand request);
    public OutputCommand getScreen(InputCommand request);

}
