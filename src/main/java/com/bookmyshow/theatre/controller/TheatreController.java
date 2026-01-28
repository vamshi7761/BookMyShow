package com.bookmyshow.theatre.controller;
import com.bookmyshow.main.commandhandler.CommandExecutor;
import com.bookmyshow.main.dto.InputCommand;
import com.bookmyshow.main.dto.OutputCommand;
import com.bookmyshow.theatre.dto.TheatreDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/theatres", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class TheatreController {

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public OutputCommand createTheatre(@RequestBody TheatreDto request) {
        InputCommand command = InputCommand.builder()
                .inputData(request)
                .action("CREATE")
                .entity("THEATRE")
                .build();

        return CommandExecutor.executeCommand(command);
    }

    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public OutputCommand getTheatreById(@RequestBody TheatreDto request) {
        InputCommand command = InputCommand.builder()
                .inputData(request)
                .action("GET")
                .entity("THEATRE")
                .build();

        return CommandExecutor.executeCommand(command);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public OutputCommand updateTheatre(@RequestBody TheatreDto request) {
        InputCommand command = InputCommand.builder()
                .inputData(request)
                .action("UPDATE")
                .entity("THEATRE")
                .build();

        return CommandExecutor.executeCommand(command);
    }

    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public OutputCommand deleteTheatre(@RequestBody TheatreDto request) {
        InputCommand command = InputCommand.builder()
                .inputData(request)
                .action("DELETE")
                .entity("THEATRE")
                .build();

        return CommandExecutor.executeCommand(command);
    }
}