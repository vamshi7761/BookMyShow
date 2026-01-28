package com.bookmyshow.role.commandhandler;

import com.bookmyshow.main.dto.InputCommand;
import com.bookmyshow.main.dto.OutputCommand;
import com.bookmyshow.role.service.RoleService;
import lombok.AllArgsConstructor;
import com.bookmyshow.main.commandhandler.CommandHandler;
import com.bookmyshow.main.commandhandler.CommandType;
import org.springframework.stereotype.Service;

@CommandType(action = "ASSIGN_PERMISSION", entity = "ROLE")
@AllArgsConstructor
@Service
public class AssignPermissionToRoleCommandHandler implements CommandHandler{

    RoleService roleService;

    @Override
    public OutputCommand execute(InputCommand request) {
        return roleService.assignPermissionToRole(request);
    }

}
