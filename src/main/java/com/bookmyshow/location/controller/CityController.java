package com.bookmyshow.location.controller;

import com.bookmyshow.location.model.City;
import com.bookmyshow.main.commandhandler.CommandExecutor;
import com.bookmyshow.main.dto.InputCommand;
import com.bookmyshow.main.dto.OutputCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/cities", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class CityController {

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public OutputCommand createCity(@RequestBody City request) {
        InputCommand command = InputCommand.builder()
                .inputData(request)
                .action("CREATE")
                .entity("CITY")
                .build();

        return CommandExecutor.executeCommand(command);
    }

    @GetMapping("/{id}")
    public OutputCommand getCityById(@PathVariable("id") Long id) {
        InputCommand command = InputCommand.builder()
                .inputData(id)
                .action("GET_BY_ID")
                .entity("CITY")
                .build();

        return CommandExecutor.executeCommand(command);
    }

    @GetMapping
    public OutputCommand getAllCities() {
        InputCommand command = InputCommand.builder()
                .action("GET_ALL")
                .entity("CITY")
                .build();

        return CommandExecutor.executeCommand(command);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public OutputCommand updateCity(@RequestBody City request) {
        InputCommand command = InputCommand.builder()
                .inputData(request)
                .action("UPDATE")
                .entity("CITY")
                .build();

        return CommandExecutor.executeCommand(command);
    }

    @DeleteMapping("/{id}")
    public OutputCommand deleteCity(@PathVariable("id") Long id) {
        InputCommand command = InputCommand.builder()
                .inputData(id)
                .action("DELETE")
                .entity("CITY")
                .build();

        return CommandExecutor.executeCommand(command);
    }
}