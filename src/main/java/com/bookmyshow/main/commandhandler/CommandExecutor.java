package com.bookmyshow.main.commandhandler;

import com.bookmyshow.main.dto.InputCommand;
import com.bookmyshow.main.dto.OutputCommand;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class CommandExecutor {

    final static Map<String, CommandHandler> commandHandlerMap = new HashMap<>();

    @Autowired
    List<CommandHandler> commandHandlers;

    @PostConstruct
    void init() {
        for (CommandHandler handler : commandHandlers) {
            CommandType commandType = handler.getClass().getAnnotation(CommandType.class);
            String key = commandType.action() + "_" + commandType.entity();
            commandHandlerMap.put(key, handler);
        }
    }
    public static OutputCommand executeCommand(InputCommand command) {
        // Logic to execute command based on commandType and commandData
        String key = command.getAction() + "_" + command.getEntity();
        CommandHandler commandHandler = commandHandlerMap.get(key);


        if (commandHandler != null) {
            return commandHandler.execute(command);
        }

        return OutputCommand.builder().build();
    }
}
