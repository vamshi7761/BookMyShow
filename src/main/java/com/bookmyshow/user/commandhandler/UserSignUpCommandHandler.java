package com.bookmyshow.user.commandhandler;

import com.bookmyshow.main.commandhandler.CommandHandler;
import com.bookmyshow.main.commandhandler.CommandType;
import com.bookmyshow.main.dto.InputCommand;
import com.bookmyshow.main.dto.OutputCommand;
import com.bookmyshow.user.userservice.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@CommandType(action = "SIGN_UP", entity = "USER")
public class UserSignUpCommandHandler implements CommandHandler {

    final  UserService userService;

    @Override
    public OutputCommand execute(InputCommand request) {
        return userService.signUp(request);
    }
}