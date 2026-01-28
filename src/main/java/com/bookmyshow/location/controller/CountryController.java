package com.bookmyshow.location.controller;
import com.bookmyshow.location.model.Country;
import com.bookmyshow.main.commandhandler.CommandExecutor;
import com.bookmyshow.main.dto.InputCommand;
import com.bookmyshow.main.dto.OutputCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/countries", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class CountryController {

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public OutputCommand createCountry(@RequestBody Country request) {
        InputCommand command = InputCommand.builder()
                .inputData(request)
                .action("CREATE")
                .entity("COUNTRY")
                .build();

        return CommandExecutor.executeCommand(command);
    }

    @GetMapping("/{id}")
    public OutputCommand getCountryById(@PathVariable("id") Long id) {
        InputCommand command = InputCommand.builder()
                .inputData(id)
                .action("GET_BY_ID")
                .entity("COUNTRY")
                .build();

        return CommandExecutor.executeCommand(command);
    }

    @GetMapping
    public OutputCommand getAllCountries() {
        InputCommand command = InputCommand.builder()
                .action("GET_ALL")
                .entity("COUNTRY")
                .build();

        return CommandExecutor.executeCommand(command);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public OutputCommand updateCountry(@RequestBody Country request) {
        InputCommand command = InputCommand.builder()
                .inputData(request)
                .action("UPDATE")
                .entity("COUNTRY")
                .build();

        return CommandExecutor.executeCommand(command);
    }

    @DeleteMapping("/{id}")
    public OutputCommand deleteCountry(@PathVariable("id") Long id) {
        InputCommand command = InputCommand.builder()
                .inputData(id)
                .action("DELETE")
                .entity("COUNTRY")
                .build();

        return CommandExecutor.executeCommand(command);
    }
}