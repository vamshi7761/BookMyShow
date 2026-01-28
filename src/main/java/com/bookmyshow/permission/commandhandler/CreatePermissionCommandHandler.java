package com.bookmyshow.permission.commandhandler;

import com.bookmyshow.main.commandhandler.CommandHandler;
import com.bookmyshow.main.commandhandler.CommandType;
import com.bookmyshow.main.dto.InputCommand;
import com.bookmyshow.main.dto.OutputCommand;
import com.bookmyshow.permission.service.PermissionService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@CommandType(action = "CREATE", entity = "PERMISSION")
@RequiredArgsConstructor
@Service
public class CreatePermissionCommandHandler implements CommandHandler {


    final PermissionService permissionService;

    @Override
    public OutputCommand execute(InputCommand request) {
        return permissionService.createPermission(request);
    }
}
