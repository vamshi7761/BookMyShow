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
@CommandType(action = "UPDATE", entity = "SCREEN")
public class UpdateScreenCommandHandler implements CommandHandler {

    private final ScreenService screenService;

    @Override
    public OutputCommand execute(InputCommand request) {
        return screenService.updateScreen(request);
    }
}