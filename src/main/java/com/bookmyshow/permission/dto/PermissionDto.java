package com.bookmyshow.permission.dto;

import lombok.Data;

@Data
public class PermissionDto {
    private Long permissionId;
    private String category;
    private String permissionName;
    private String description;
    private String message;
}
