package com.bookmyshow.user.controller;

import com.bookmyshow.main.commandhandler.CommandExecutor;
import com.bookmyshow.main.dto.InputCommand;
import com.bookmyshow.main.dto.OutputCommand;
import com.bookmyshow.user.dto.UserLoginRequest;
import com.bookmyshow.user.dto.UserSignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UserController {

    @PostMapping(value = "api/users/signup", consumes = MediaType.APPLICATION_JSON_VALUE)
    public OutputCommand signUpUser(@RequestBody UserSignUpRequest request) {
        InputCommand command = InputCommand.builder()
                .inputData(request)
                .action("SIGN_UP")
                .entity("USER")
                .build();
        OutputCommand outCommand = CommandExecutor.executeCommand(command);
        System.out.println("User signed up: ");
        return outCommand;
    }

    @PostMapping(value = "api/users/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public OutputCommand loginUser(@RequestBody UserLoginRequest request) {
        InputCommand command = InputCommand.builder()
                .inputData(request)
                .action("LOGIN")
                .entity("USER")
                .build();
        return CommandExecutor.executeCommand(command);
    }

    @GetMapping(value = "admin/health")
    @PreAuthorize("hasAuthority('ADMIN_ACCESS')")
    public String healthCheck() {
        return "admin Service is up and running!";
    }

    @GetMapping(value = "branch/health")
    @PreAuthorize("hasAuthority('BRANCH_ACCESS')")
    public String branchHealthCheck() {
        return "Branch Service is up and running!";
    }

    @GetMapping(value = "enduser/health")
    @PreAuthorize("hasAuthority('END_USER_ACCESS')")
    public String endUserHealthCheck() {
        return "End User Service is up and running!";
    }

}