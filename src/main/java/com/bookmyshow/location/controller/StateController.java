package com.bookmyshow.location.controller;

import com.bookmyshow.location.model.State;
import com.bookmyshow.main.commandhandler.CommandExecutor;
import com.bookmyshow.main.dto.InputCommand;
import com.bookmyshow.main.dto.OutputCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/states", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class StateController {

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public OutputCommand createState(@RequestBody State request) {
        InputCommand command = InputCommand.builder()
                .inputData(request)
                .action("CREATE")
                .entity("STATE")
                .build();

        return CommandExecutor.executeCommand(command);
    }

    @GetMapping("/{id}")
    public OutputCommand getStateById(@PathVariable("id") Long id) {
        InputCommand command = InputCommand.builder()
                .inputData(id)
                .action("GET_BY_ID")
                .entity("STATE")
                .build();

        return CommandExecutor.executeCommand(command);
    }

    @GetMapping
    public OutputCommand getAllStates() {
        InputCommand command = InputCommand.builder()
                .action("GET_ALL")
                .entity("STATE")
                .build();

        return CommandExecutor.executeCommand(command);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public OutputCommand updateState(@RequestBody State request) {
        InputCommand command = InputCommand.builder()
                .inputData(request)
                .action("UPDATE")
                .entity("STATE")
                .build();

        return CommandExecutor.executeCommand(command);
    }

    @DeleteMapping("/{id}")
    public OutputCommand deleteState(@PathVariable("id") Long id) {
        InputCommand command = InputCommand.builder()
                .inputData(id)
                .action("DELETE")
                .entity("STATE")
                .build();

        return CommandExecutor.executeCommand(command);
    }
}