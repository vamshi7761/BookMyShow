package com.bookmyshow.theatre.commandhandler.screen;

import com.bookmyshow.main.commandhandler.CommandHandler;
import com.bookmyshow.main.commandhandler.CommandType;
import com.bookmyshow.main.dto.InputCommand;
import com.bookmyshow.main.dto.OutputCommand;
import com.bookmyshow.theatre.service.ScreenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@CommandType(action = "GET", entity = "SCREEN")
public class GetScreenCommandHandler implements CommandHandler {

    private final ScreenService screenService;

    @Override
    public OutputCommand execute(InputCommand request) {
        return screenService.getScreen(request);
    }
}