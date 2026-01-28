package com.bookmyshow.theatre.controller;

import com.bookmyshow.main.commandhandler.CommandExecutor;
import com.bookmyshow.main.dto.InputCommand;
import com.bookmyshow.main.dto.OutputCommand;
import com.bookmyshow.theatre.dto.SeatDtoList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/seats", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class SeatController {

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public OutputCommand addSeatsToScreen(@RequestBody SeatDtoList request) {
        InputCommand command = InputCommand.builder()
                .inputData(request)
                .action("ADD")
                .entity("SEAT")
                .build();

        return CommandExecutor.executeCommand(command);
    }

    @GetMapping("/{screenId}")
    public OutputCommand getSeatsByScreenId(@RequestBody SeatDtoList request) {
        InputCommand command = InputCommand.builder()
                .inputData(request)
                .action("GET")
                .entity("SEAT")
                .build();

        return CommandExecutor.executeCommand(command);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public OutputCommand updateSeatsInScreen(@RequestBody SeatDtoList request) {
        InputCommand command = InputCommand.builder()
                .inputData(request)
                .action("UPDATE")
                .entity("SEAT")
                .build();

        return CommandExecutor.executeCommand(command);
    }

    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public OutputCommand deleteSeatsFromScreen(@RequestBody SeatDtoList request) {
        InputCommand command = InputCommand.builder()
                .inputData(request)
                .action("DELETE")
                .entity("SEAT")
                .build();

        return CommandExecutor.executeCommand(command);
    }
}