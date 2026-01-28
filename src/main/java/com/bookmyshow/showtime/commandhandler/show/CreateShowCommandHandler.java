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
@CommandType(action = "CREATE", entity = "SHOW")
public class CreateShowCommandHandler implements CommandHandler {

    private final ShowService showService;

    @Override
    public OutputCommand execute(InputCommand request) {
        // Delegates to your show creation service method that returns OutputCommand
        return showService.createShow(request);
    }
}
