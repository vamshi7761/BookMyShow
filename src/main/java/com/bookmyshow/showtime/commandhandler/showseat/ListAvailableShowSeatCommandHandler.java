package com.bookmyshow.showtime.commandhandler.showseat;

import com.bookmyshow.main.commandhandler.CommandHandler;
import com.bookmyshow.main.commandhandler.CommandType;
import com.bookmyshow.main.dto.InputCommand;
import com.bookmyshow.main.dto.OutputCommand;
import com.bookmyshow.showtime.service.ShowSeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@CommandType(action = "AVAILABLE", entity = "SHOW_SEAT")
public class ListAvailableShowSeatCommandHandler implements CommandHandler {

    private final ShowSeatService showSeatService;

    @Override
    public OutputCommand execute(InputCommand request) {
        return showSeatService.getAvailableShowSeats(request);
    }
}
