package com.bookmyshow.location.commandhandler.state;

import com.bookmyshow.location.service.StateService;
import com.bookmyshow.main.commandhandler.CommandHandler;
import com.bookmyshow.main.commandhandler.CommandType;
import com.bookmyshow.main.dto.InputCommand;
import com.bookmyshow.main.dto.OutputCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@CommandType(action = "UPDATE", entity = "STATE")
public class UpdateStateCommandHandler implements CommandHandler {

    private final StateService stateService;

    @Override
    public OutputCommand execute(InputCommand request) {
        return stateService.updateState(request);
    }
}