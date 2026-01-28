package com.bookmyshow.role.commandhandler;

import com.bookmyshow.main.commandhandler.CommandHandler;
import com.bookmyshow.main.commandhandler.CommandType;
import com.bookmyshow.main.dto.InputCommand;
import com.bookmyshow.main.dto.OutputCommand;
import com.bookmyshow.role.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@CommandType(action = "REMOVE_PERMISSION", entity = "ROLE")
@AllArgsConstructor
@Service
public class RemovePermissionFromRole implements CommandHandler {

    RoleService roleService;

    @Override
    public OutputCommand execute(InputCommand request) {
        return roleService.removePermissionFromRole(request);
    }
}
