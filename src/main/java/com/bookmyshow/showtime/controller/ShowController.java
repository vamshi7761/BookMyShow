package com.bookmyshow.showtime.controller;

import com.bookmyshow.main.commandhandler.CommandExecutor;
import com.bookmyshow.main.dto.InputCommand;
import com.bookmyshow.main.dto.OutputCommand;
import com.bookmyshow.showtime.dto.ShowDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/shows", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ShowController {

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public OutputCommand createShow(@RequestBody ShowDto request) {
        InputCommand command = InputCommand.builder()
                .inputData(request)
                .action("CREATE")
                .entity("SHOW")
                .build();

        return CommandExecutor.executeCommand(command);
    }

    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public OutputCommand getShow(@RequestBody ShowDto request) {
        InputCommand command = InputCommand.builder()
                .inputData(request)
                .action("GET")
                .entity("SHOW")
                .build();

        return CommandExecutor.executeCommand(command);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public OutputCommand updateShow(@RequestBody ShowDto request) {
        InputCommand command = InputCommand.builder()
                .inputData(request)
                .action("UPDATE")
                .entity("SHOW")
                .build();

        return CommandExecutor.executeCommand(command);
    }

    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public OutputCommand deleteShow(@RequestBody ShowDto request) {
        InputCommand command = InputCommand.builder()
                .inputData(request)
                .action("DELETE")
                .entity("SHOW")
                .build();

        return CommandExecutor.executeCommand(command);
    }
}
