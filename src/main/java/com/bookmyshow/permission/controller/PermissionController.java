
package com.bookmyshow.permission.controller;

import com.bookmyshow.main.commandhandler.CommandExecutor;
import com.bookmyshow.main.dto.InputCommand;
import com.bookmyshow.main.dto.OutputCommand;
import com.bookmyshow.permission.dto.PermissionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/permissions", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class PermissionController {

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public OutputCommand createPermission(@RequestBody PermissionDto request) {
        InputCommand command = InputCommand.builder()
                .inputData(request)
                .action("CREATE")
                .entity("PERMISSION")
                .build();

        return CommandExecutor.executeCommand(command);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public OutputCommand updatePermission(@PathVariable("id") Long id,
                                          @RequestBody PermissionDto request) {

        request.setPermissionId(id);

        InputCommand command = InputCommand.builder()
                .inputData(request)
                .action("UPDATE")
                .entity("PERMISSION")
                .build();

        return CommandExecutor.executeCommand(command);
    }

    @DeleteMapping("/{id}")
    public OutputCommand deletePermission(@PathVariable("id") Long id) {
        InputCommand command = InputCommand.builder()
                .inputData(id)
                .action("DELETE")
                .entity("PERMISSION")
                .build();

        return CommandExecutor.executeCommand(command);
    }
}
