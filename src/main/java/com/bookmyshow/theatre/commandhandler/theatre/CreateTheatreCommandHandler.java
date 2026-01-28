package com.bookmyshow.theatre.commandhandler.theatre;

import com.bookmyshow.main.commandhandler.CommandHandler;
import com.bookmyshow.main.commandhandler.CommandType;
import com.bookmyshow.main.dto.InputCommand;
import com.bookmyshow.main.dto.OutputCommand;
import com.bookmyshow.theatre.service.TheatreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@CommandType(action = "CREATE", entity = "THEATRE")
public class CreateTheatreCommandHandler implements CommandHandler {

    private final TheatreService theatreService;

    @Override
    public OutputCommand execute(InputCommand request) {
        return theatreService.createTheatre(request);
    }
}