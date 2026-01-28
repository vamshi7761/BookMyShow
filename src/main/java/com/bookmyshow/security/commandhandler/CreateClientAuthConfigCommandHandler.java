package com.bookmyshow.security.commandhandler;

import com.bookmyshow.main.commandhandler.CommandHandler;
import com.bookmyshow.main.commandhandler.CommandType;
import com.bookmyshow.main.dto.InputCommand;
import com.bookmyshow.main.dto.OutputCommand;
import com.bookmyshow.security.service.ClientAuthConfigService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@CommandType(action = "CREATE", entity = "CLIENT_AUTH_CONFIG")
@Service
@AllArgsConstructor
public class CreateClientAuthConfigCommandHandler implements CommandHandler {

    private final ClientAuthConfigService clientAuthConfigService;

    @Override
    public OutputCommand execute(InputCommand request) {

        return clientAuthConfigService.createClientAuthConfig(request);
    }
}
