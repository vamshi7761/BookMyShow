package com.bookmyshow.showtime.commandhandler.showseattype;

import com.bookmyshow.main.commandhandler.CommandHandler;
import com.bookmyshow.main.commandhandler.CommandType;
import com.bookmyshow.main.dto.InputCommand;
import com.bookmyshow.main.dto.OutputCommand;
import com.bookmyshow.showtime.service.ShowSeatTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@CommandType(action = "LIST_BY_SHOW", entity = "SHOW_SEAT_TYPE")
public class ListByShowShowSeatTypeCommandHandler implements CommandHandler {

    private final ShowSeatTypeService showSeatTypeService;

    @Override
    public OutputCommand execute(InputCommand request) {
        return showSeatTypeService.listSeatTypesForShow(request);
    }
}
