package com.bookmyshow.role.service;

import com.bookmyshow.main.dto.InputCommand;
import com.bookmyshow.main.dto.OutputCommand;
import com.bookmyshow.role.dto.RoleDto;
import com.bookmyshow.permission.model.Permission;
import com.bookmyshow.role.model.Role;
import com.bookmyshow.permission.repository.PermissionRepository;
import com.bookmyshow.role.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    public OutputCommand createRole(InputCommand request) {
        RoleDto roleDto = (RoleDto) request.getInputData();

        if (roleRepository.existsByRoleName(roleDto.getRoleName())) {
            roleDto.setMessage("Role already exists");
            return OutputCommand.builder().outputData(roleDto).build();
        }

        Role role = new Role();
        role.setRoleName(roleDto.getRoleName());
        role.setDescription(roleDto.getDescription());

        if (roleDto.getPermissionIds() != null && !roleDto.getPermissionIds().isEmpty()) {
            List<Permission> permissions = permissionRepository.findAllById(roleDto.getPermissionIds());
            role.setPermissionList(permissions);
        }

        Role savedRole = roleRepository.save(role);
        RoleDto response = convertToDto(savedRole);
        response.setMessage("Role created successfully");

        return OutputCommand.builder().outputData(response).build();
    }

    public OutputCommand updateRole(InputCommand request) {
        RoleDto roleDto = (RoleDto) request.getInputData();

        Role role = roleRepository.findById(roleDto.getRoleId())
                .orElseThrow(() -> new IllegalArgumentException("Role not found"));

        if (roleDto.getRoleName() != null) {
            role.setRoleName(roleDto.getRoleName());
        }
        if (roleDto.getDescription() != null) {
            role.setDescription(roleDto.getDescription());
        }

        if (roleDto.getPermissionIds() != null) {
            List<Permission> permissions = permissionRepository.findAllById(roleDto.getPermissionIds());
            role.setPermissionList(permissions);
        }

        Role updatedRole = roleRepository.save(role);
        RoleDto response = convertToDto(updatedRole);
        response.setMessage("Role updated successfully");

        return OutputCommand.builder().outputData(response).build();
    }

    public OutputCommand deleteRole(InputCommand request) {
        RoleDto roleDto = (RoleDto) request.getInputData();

        if (!roleRepository.existsById(roleDto.getRoleId())) {
            roleDto.setMessage("Role not found");
            return OutputCommand.builder().outputData(roleDto).build();
        }

        roleRepository.deleteById(roleDto.getRoleId());
        roleDto.setMessage("Role deleted successfully");

        return OutputCommand.builder().outputData(roleDto).build();
    }

    public OutputCommand assignPermissionToRole(InputCommand request) {
        RoleDto roleDto = (RoleDto) request.getInputData();

        Role role = roleRepository.findById(roleDto.getRoleId())
                .orElseThrow(() -> new IllegalArgumentException("Role not found"));

        List<Permission> permissions = permissionRepository.findAllById(roleDto.getPermissionIds());

        if (permissions.isEmpty()) {
            roleDto.setMessage("No valid permissions found");
            return OutputCommand.builder().outputData(roleDto).build();
        }

        if (role.getPermissionList() == null) {
            role.setPermissionList(new ArrayList<>());
        }

        for (Permission permission : permissions) {
            if (!role.getPermissionList().contains(permission)) {
                role.getPermissionList().add(permission);
            }
        }

        Role updatedRole = roleRepository.save(role);
        RoleDto response = convertToDto(updatedRole);
        response.setMessage("Permissions assigned successfully");

        return OutputCommand.builder().outputData(response).build();
    }

    public OutputCommand removePermissionFromRole(InputCommand request) {
        RoleDto roleDto = (RoleDto) request.getInputData();

        Role role = roleRepository.findById(roleDto.getRoleId())
                .orElseThrow(() -> new IllegalArgumentException("Role not found"));

        if (role.getPermissionList() == null || role.getPermissionList().isEmpty()) {
            roleDto.setMessage("No permissions to remove");
            return OutputCommand.builder().outputData(roleDto).build();
        }

        List<Permission> permissionsToRemove = permissionRepository.findAllById(roleDto.getPermissionIds());
        role.getPermissionList().removeAll(permissionsToRemove);

        Role updatedRole = roleRepository.save(role);
        RoleDto response = convertToDto(updatedRole);
        response.setMessage("Permissions removed successfully");

        return OutputCommand.builder().outputData(response).build();
    }

    private RoleDto convertToDto(Role role) {
        RoleDto dto = new RoleDto();
        dto.setRoleId(role.getId());
        dto.setRoleName(role.getRoleName());
        dto.setDescription(role.getDescription());

        if (role.getPermissionList() != null) {
            dto.setPermissionIds(role.getPermissionList().stream()
                    .map(Permission::getId)
                    .collect(Collectors.toList()));
        }

        return dto;
    }
}