package com.bookmyshow.location.model;


import com.bookmyshow.main.model.BaseModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Entity
@Table(name = "countries", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"code"}),
        @UniqueConstraint(columnNames = {"name"})
})
@Data
public class Country extends BaseModel {

    @Column(name = "code", nullable = false, length = 2)
    private String countryCode;

    @Column(name = "name", nullable = false, length = 100)
    private String countryName;
}
