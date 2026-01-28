
package com.bookmyshow.showtime.controller;

import com.bookmyshow.main.commandhandler.CommandExecutor;
import com.bookmyshow.main.dto.InputCommand;
import com.bookmyshow.main.dto.OutputCommand;
import com.bookmyshow.showtime.dto.ShowSeatTypeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/show-seat-types", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ShowSeatTypeController {

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public OutputCommand create(@RequestBody ShowSeatTypeDto request) {
        InputCommand command = InputCommand.builder()
                .inputData(request)
                .action("CREATE")
                .entity("SHOW_SEAT_TYPE")
                .build();
        return CommandExecutor.executeCommand(command);
    }

    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public OutputCommand get(@RequestBody ShowSeatTypeDto request) {
        InputCommand command = InputCommand.builder()
                .inputData(request)
                .action("GET")
                .entity("SHOW_SEAT_TYPE")
                .build();
        return CommandExecutor.executeCommand(command);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public OutputCommand update(@RequestBody ShowSeatTypeDto request) {
        InputCommand command = InputCommand.builder()
                .inputData(request)
                .action("UPDATE")
                .entity("SHOW_SEAT_TYPE")
                .build();
        return CommandExecutor.executeCommand(command);
    }

    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public OutputCommand delete(@RequestBody ShowSeatTypeDto request) {
        InputCommand command = InputCommand.builder()
                .inputData(request)
                .action("DELETE")
                .entity("SHOW_SEAT_TYPE")
                .build();
        return CommandExecutor.executeCommand(command);
    }

    // Optional: list all seat-type prices for a show
    @PostMapping(value = "/by-show", consumes = MediaType.APPLICATION_JSON_VALUE)
    public OutputCommand listByShow(@RequestBody ShowSeatTypeDto request) {
        InputCommand command = InputCommand.builder()
                .inputData(request)
                .action("LIST_BY_SHOW")
                .entity("SHOW_SEAT_TYPE")
                .build();
        return CommandExecutor.executeCommand(command);
    }
}
