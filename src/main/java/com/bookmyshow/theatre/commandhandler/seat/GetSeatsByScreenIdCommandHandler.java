package com.bookmyshow.theatre.commandhandler.seat;

import com.bookmyshow.main.commandhandler.CommandHandler;
import com.bookmyshow.main.commandhandler.CommandType;
import com.bookmyshow.main.dto.InputCommand;
import com.bookmyshow.main.dto.OutputCommand;
import com.bookmyshow.theatre.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@CommandType(action = "GET", entity = "SEAT")
public class GetSeatsByScreenIdCommandHandler implements CommandHandler {

    private final SeatService seatService;

    @Override
    public OutputCommand execute(InputCommand request) {
        return seatService.getSeatsByScreenId(request);
    }
}