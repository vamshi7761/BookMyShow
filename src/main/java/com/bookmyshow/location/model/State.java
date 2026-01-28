package com.bookmyshow.location.model;


import com.bookmyshow.main.model.BaseModel;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "states", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"country_id", "state_name"})
})
@Data
public class State extends BaseModel {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    @Column(name = "state_name", nullable = false, length = 100)
    private String stateName;

    @Column(name = "state_code", nullable = false, length = 10)
    private String stateCode;
}
