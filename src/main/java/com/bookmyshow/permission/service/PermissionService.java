package com.bookmyshow.permission.service;

import com.bookmyshow.main.dto.InputCommand;
import com.bookmyshow.main.dto.OutputCommand;
import com.bookmyshow.permission.dto.PermissionDto;
import com.bookmyshow.permission.model.Permission;
import com.bookmyshow.permission.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PermissionService {

    private final PermissionRepository permissionRepository;

    public OutputCommand createPermission(InputCommand request) {
        PermissionDto permissionDto = (PermissionDto) request.getInputData();
        if (permissionRepository.existsByPermissionName(permissionDto.getPermissionName())) {
            throw new IllegalArgumentException("Permission already exists");
        }

        Permission permission = new Permission();
        permission.setPermissionName(permissionDto.getPermissionName());
        permission.setDescription(permissionDto.getDescription());
        permission.setCategory(permissionDto.getCategory());

        permissionRepository.save(permission);
        permissionDto.setPermissionId(permission.getId());
        permissionDto.setMessage("Permission created successfully");

        return OutputCommand.builder()
                .outputData(permissionDto)
                .build();
    }

    public OutputCommand updatePermission(InputCommand request) {
        PermissionDto permissionDto = (PermissionDto) request.getInputData();

        Permission permission = permissionRepository.findById(permissionDto.getPermissionId())
                .orElseThrow(() -> new IllegalArgumentException("Permission not found"));


        if(permissionDto.getPermissionName() != null)
        {
            permission.setPermissionName(permissionDto.getPermissionName());
        }
        if(permissionDto.getCategory() != null)
        {
            permission.setCategory(permissionDto.getCategory());
        }
        if (permissionDto.getDescription() != null) {
            permission.setDescription(permissionDto.getDescription());
        }

        Permission updated = permissionRepository.save(permission);
        permissionDto.setMessage("Permission updated successfully");
        return OutputCommand.builder()
                .outputData(permissionDto)
                .build();
    }

    public OutputCommand deletePermission(InputCommand request) {

        Long id = (Long) request.getInputData();
        if (!permissionRepository.existsById(id)) {
            throw new IllegalArgumentException("Permission not found");
        }
        permissionRepository.deleteById(id);
        return OutputCommand.builder()
                .outputData("Permission deleted successfully : ID " + id)
                .build();
    }
}