package com.bookmyshow.showtime.commandhandler.show;

import com.bookmyshow.main.commandhandler.CommandHandler;
import com.bookmyshow.main.commandhandler.CommandType;
import com.bookmyshow.main.dto.InputCommand;
import com.bookmyshow.main.dto.OutputCommand;
import com.bookmyshow.showtime.service.ShowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@CommandType(action = "DELETE", entity = "SHOW")
class DeleteShowCommandHandler implements CommandHandler {
    private final ShowService showService;
    @Override
    public OutputCommand execute(InputCommand request) {
        return showService.deleteShow(request);
    }
}