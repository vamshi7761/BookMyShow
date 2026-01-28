package com.bookmyshow.role.model;

import com.bookmyshow.main.model.BaseModel;
import com.bookmyshow.permission.model.Permission;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Role extends BaseModel {

    @Column(nullable = false, unique = true)
    private String roleName;

    @Column(nullable = false)
    private String description;

    @ManyToMany
    private List<Permission> permissionList;
}
