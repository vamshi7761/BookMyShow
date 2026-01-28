package com.bookmyshow.moviecatalog.controller;

import com.bookmyshow.main.commandhandler.CommandExecutor;
import com.bookmyshow.main.dto.InputCommand;
import com.bookmyshow.main.dto.OutputCommand;
import com.bookmyshow.moviecatalog.dto.MovieDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/movies", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class MovieController {

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public OutputCommand createMovie(@RequestBody MovieDto request) {
        InputCommand command = InputCommand.builder()
                .inputData(request)
                .action("CREATE")
                .entity("MOVIE")
                .build();

        return CommandExecutor.executeCommand(command);
    }

    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public OutputCommand getMovie(@RequestBody MovieDto request) {
        InputCommand command = InputCommand.builder()
                .inputData(request)
                .action("GET")
                .entity("MOVIE")
                .build();

        return CommandExecutor.executeCommand(command);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public OutputCommand updateMovie(@RequestBody MovieDto request) {
        InputCommand command = InputCommand.builder()
                .inputData(request)
                .action("UPDATE")
                .entity("MOVIE")
                .build();

        return CommandExecutor.executeCommand(command);
    }

    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public OutputCommand deleteMovie(@RequestBody MovieDto request) {
        InputCommand command = InputCommand.builder()
                .inputData(request)
                .action("DELETE")
                .entity("MOVIE")
                .build();

        return CommandExecutor.executeCommand(command);
    }
}