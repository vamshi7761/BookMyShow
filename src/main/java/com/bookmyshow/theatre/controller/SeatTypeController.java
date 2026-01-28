package com.bookmyshow.theatre.controller;

import com.bookmyshow.main.commandhandler.CommandExecutor;
import com.bookmyshow.main.dto.InputCommand;
import com.bookmyshow.main.dto.OutputCommand;
import com.bookmyshow.theatre.dto.SeatTypeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/seat-types", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class SeatTypeController {

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public OutputCommand createSeatType(@RequestBody SeatTypeDto request) {
        InputCommand command = InputCommand.builder()
                .inputData(request)
                .action("CREATE")
                .entity("SEATTYPE")
                .build();

        return CommandExecutor.executeCommand(command);
    }

    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public OutputCommand getSeatType(@RequestBody SeatTypeDto request) {
        InputCommand command = InputCommand.builder()
                .inputData(request)
                .action("GET")
                .entity("SEATTYPE")
                .build();

        return CommandExecutor.executeCommand(command);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public OutputCommand updateSeatType(@RequestBody SeatTypeDto request) {
        InputCommand command = InputCommand.builder()
                .inputData(request)
                .action("UPDATE")
                .entity("SEATTYPE")
                .build();

        return CommandExecutor.executeCommand(command);
    }

    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public OutputCommand deleteSeatType(@RequestBody SeatTypeDto request) {
        InputCommand command = InputCommand.builder()
                .inputData(request)
                .action("DELETE")
                .entity("SEATTYPE")
                .build();

        return CommandExecutor.executeCommand(command);
    }
}