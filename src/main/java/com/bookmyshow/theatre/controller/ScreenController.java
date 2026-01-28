package com.bookmyshow.theatre.controller;

import com.bookmyshow.main.commandhandler.CommandExecutor;
import com.bookmyshow.main.dto.InputCommand;
import com.bookmyshow.main.dto.OutputCommand;
import com.bookmyshow.theatre.dto.ScreenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/screens", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ScreenController {

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public OutputCommand createScreen(@RequestBody ScreenDto request) {
        InputCommand command = InputCommand.builder()
                .inputData(request)
                .action("CREATE")
                .entity("SCREEN")
                .build();

        return CommandExecutor.executeCommand(command);
    }

    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public OutputCommand getScreenById(@RequestBody ScreenDto request) {
        InputCommand command = InputCommand.builder()
                .inputData(request)
                .action("GET")
                .entity("SCREEN")
                .build();

        return CommandExecutor.executeCommand(command);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public OutputCommand updateScreen(@RequestBody ScreenDto request) {
        InputCommand command = InputCommand.builder()
                .inputData(request)
                .action("UPDATE")
                .entity("SCREEN")
                .build();

        return CommandExecutor.executeCommand(command);
    }

    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public OutputCommand deleteScreen(@RequestBody ScreenDto request) {
        InputCommand command = InputCommand.builder()
                .inputData(request)
                .action("DELETE")
                .entity("SCREEN")
                .build();

        return CommandExecutor.executeCommand(command);
    }
}