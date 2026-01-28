package com.bookmyshow.location.commandhandler.city;

import com.bookmyshow.location.service.CityService;
import com.bookmyshow.main.commandhandler.CommandHandler;
import com.bookmyshow.main.commandhandler.CommandType;
import com.bookmyshow.main.dto.InputCommand;
import com.bookmyshow.main.dto.OutputCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@CommandType(action = "DELETE", entity = "CITY")
public class DeleteCityCommandHandler implements CommandHandler {

    private final CityService cityService;

    @Override
    public OutputCommand execute(InputCommand request) {
        return cityService.deleteCity(request);
    }
}