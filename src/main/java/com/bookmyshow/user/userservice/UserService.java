package com.bookmyshow.user.userservice;

import com.bookmyshow.main.dto.InputCommand;
import com.bookmyshow.main.dto.OutputCommand;

public interface UserService {

    public OutputCommand signUp(InputCommand inputCommand);

    public OutputCommand login(InputCommand inputCommand);
}
