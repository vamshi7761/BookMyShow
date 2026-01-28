package com.bookmyshow.showtime.controller;


import com.bookmyshow.main.commandhandler.CommandExecutor;
import com.bookmyshow.main.dto.InputCommand;
import com.bookmyshow.main.dto.OutputCommand;
import com.bookmyshow.showtime.dto.ShowSeatDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/show-seats", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ShowSeatController {

    @PostMapping(value = "/generate", consumes = MediaType.APPLICATION_JSON_VALUE)
    public OutputCommand generateShowSeats(@RequestBody ShowSeatDto request) {
        InputCommand command = InputCommand.builder()
                .inputData(request)
                .action("GENERATE")
                .entity("SHOW_SEAT")
                .build();

        return CommandExecutor.executeCommand(command);
    }

    @PostMapping(value = "/list", consumes = MediaType.APPLICATION_JSON_VALUE)
    public OutputCommand listShowSeats(@RequestBody ShowSeatDto request) {
        InputCommand command = InputCommand.builder()
                .inputData(request)
                .action("LIST")
                .entity("SHOW_SEAT")
                .build();

        return CommandExecutor.executeCommand(command);
    }

    @PostMapping(value = "/available", consumes = MediaType.APPLICATION_JSON_VALUE)
    public OutputCommand listAvailableShowSeats(@RequestBody ShowSeatDto request) {
        InputCommand command = InputCommand.builder()
                .inputData(request)
                .action("AVAILABLE")
                .entity("SHOW_SEAT")
                .build();

        return CommandExecutor.executeCommand(command);
    }
}

