package com.bookmyshow.location.commandhandler.country;
import com.bookmyshow.location.service.CountryService;
import com.bookmyshow.main.commandhandler.CommandHandler;
import com.bookmyshow.main.commandhandler.CommandType;
import com.bookmyshow.main.dto.InputCommand;
import com.bookmyshow.main.dto.OutputCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@CommandType(action = "GET_ALL", entity = "COUNTRY")
public class GetAllCountriesCommandHandler implements CommandHandler {

    private final CountryService countryService;

    @Override
    public OutputCommand execute(InputCommand request) {
        return countryService.getAllCountries(request);
    }
}