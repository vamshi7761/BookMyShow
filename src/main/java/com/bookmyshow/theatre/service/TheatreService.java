package com.bookmyshow.theatre.service;

import com.bookmyshow.main.dto.InputCommand;
import com.bookmyshow.main.dto.OutputCommand;

public interface TheatreService {

    public OutputCommand createTheatre(InputCommand request);
    public OutputCommand updateTheatre(InputCommand request);
    public OutputCommand deleteTheatre(InputCommand request);
    public OutputCommand getTheatre(InputCommand request);

}
