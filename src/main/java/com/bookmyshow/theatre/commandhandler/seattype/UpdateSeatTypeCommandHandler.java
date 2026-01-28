package com.bookmyshow.theatre.commandhandler.seattype;

import com.bookmyshow.main.commandhandler.CommandHandler;
import com.bookmyshow.main.commandhandler.CommandType;
import com.bookmyshow.main.dto.InputCommand;
import com.bookmyshow.main.dto.OutputCommand;
import com.bookmyshow.theatre.service.SeatTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@CommandType(action = "UPDATE", entity = "SEATTYPE")
public class UpdateSeatTypeCommandHandler implements CommandHandler {

    private final SeatTypeService seatTypeService;

    @Override
    public OutputCommand execute(InputCommand request) {
        return seatTypeService.updateSeatType(request);
    }
}