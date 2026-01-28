package com.bookmyshow.role.controller;

import com.bookmyshow.main.commandhandler.CommandExecutor;
import com.bookmyshow.main.dto.InputCommand;
import com.bookmyshow.main.dto.OutputCommand;
import com.bookmyshow.role.dto.RoleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/roles", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class RoleController {

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public OutputCommand createRole(@RequestBody RoleDto request) {
        InputCommand command = InputCommand.builder()
                .inputData(request)
                .action("CREATE")
                .entity("ROLE")
                .build();

        return CommandExecutor.executeCommand(command);
    }

    @PutMapping( consumes = MediaType.APPLICATION_JSON_VALUE)
    public OutputCommand updateRole(@RequestBody RoleDto request) {

        InputCommand command = InputCommand.builder()
                .inputData(request)
                .action("UPDATE")
                .entity("ROLE")
                .build();

        return CommandExecutor.executeCommand(command);
    }

    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public OutputCommand deleteRole(@RequestBody RoleDto request) {

        InputCommand command = InputCommand.builder()
                .inputData(request)
                .action("DELETE")
                .entity("ROLE")
                .build();

        return CommandExecutor.executeCommand(command);
    }

    @PostMapping(value = "/map/permissions", consumes = MediaType.APPLICATION_JSON_VALUE)
    public OutputCommand assignPermissions(@RequestBody RoleDto request) {

        InputCommand command = InputCommand.builder()
                .inputData(request)
                .action("ASSIGN_PERMISSION")
                .entity("ROLE")
                .build();

        return CommandExecutor.executeCommand(command);
    }

    @DeleteMapping(value = "/unmap/permissions", consumes = MediaType.APPLICATION_JSON_VALUE)
    public OutputCommand removePermissions(@RequestBody RoleDto request) {

        InputCommand command = InputCommand.builder()
                .inputData(request)
                .action("REMOVE_PERMISSION")
                .entity("ROLE")
                .build();

        return CommandExecutor.executeCommand(command);
    }
}
