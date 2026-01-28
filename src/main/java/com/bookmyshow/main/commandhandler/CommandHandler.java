package com.bookmyshow.main.commandhandler;


import com.bookmyshow.main.dto.InputCommand;
import com.bookmyshow.main.dto.OutputCommand;

public interface CommandHandler {
    public OutputCommand execute(InputCommand request);
}