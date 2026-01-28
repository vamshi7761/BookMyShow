package com.bookmyshow.role.dto;

import lombok.Data;

import java.util.List;

@Data
public class RoleDto {

    private Long roleId;
    private String roleName;
    private String description;
    private String message;
    private List<Long> permissionIds;

}
