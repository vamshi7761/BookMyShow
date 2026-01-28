package com.bookmyshow.permission.model;

import com.bookmyshow.main.model.BaseModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Permission extends BaseModel {


    @Column(nullable = false)
    private String category;

    @Column(nullable = false, unique = true)
    private String permissionName;

    @Column(nullable = false)
    private String description;

}
