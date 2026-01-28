package com.bookmyshow.permission.commandhandler;

import com.bookmyshow.main.commandhandler.CommandHandler;
import com.bookmyshow.main.commandhandler.CommandType;
import com.bookmyshow.main.dto.InputCommand;
import com.bookmyshow.main.dto.OutputCommand;
import com.bookmyshow.permission.service.PermissionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@CommandType(action = "DELETE", entity = "PERMISSION")
@AllArgsConstructor
@Service
public class DeletePermissionCommandHandler implements CommandHandler {


    final PermissionService permissionService;

    @Override
    public OutputCommand execute(InputCommand request) {
        return permissionService.deletePermission(request);
    }
}
