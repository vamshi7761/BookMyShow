package com.bookmyshow.security.controller;

import com.bookmyshow.main.commandhandler.CommandExecutor;
import com.bookmyshow.main.commandhandler.CommandHandler;
import com.bookmyshow.main.dto.InputCommand;
import com.bookmyshow.main.dto.OutputCommand;
import com.bookmyshow.security.model.ClientAuthConfig;
import com.bookmyshow.security.service.ClientAuthConfigService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/client-auth-config")
@AllArgsConstructor
public class ClientAuthConfigController {


    @PostMapping
    public OutputCommand createClientAuthConfig(@RequestBody ClientAuthConfig request) {

        InputCommand inputCommand = InputCommand
                .builder()
                .entity("CLIENT_AUTH_CONFIG")
                .action("CREATE")
                .inputData(request)
                .build();

        return CommandExecutor.executeCommand(inputCommand);
    }
}