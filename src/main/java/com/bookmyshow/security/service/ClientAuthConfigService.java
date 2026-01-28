package com.bookmyshow.security.service;


import com.bookmyshow.main.commandhandler.CommandType;
import com.bookmyshow.main.dto.InputCommand;
import com.bookmyshow.main.dto.OutputCommand;
import com.bookmyshow.security.model.ClientAuthConfig;
import com.bookmyshow.security.repository.ClientAuthConfigRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@CommandType(action = "CREATE", entity = "CLIENT_AUTH_CONFIG")
public class ClientAuthConfigService  {

    private final ClientAuthConfigRepository clientAuthConfigRepository;

    public OutputCommand createClientAuthConfig(InputCommand request) {

        ClientAuthConfig clientAuthConfig = (ClientAuthConfig) request.getInputData();
        ClientAuthConfig savedConfig = clientAuthConfigRepository.save(clientAuthConfig);
        return OutputCommand.builder().outputData(savedConfig).build();

    }

}
