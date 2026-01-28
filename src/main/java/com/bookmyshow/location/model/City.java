package com.bookmyshow.location.model;


import com.bookmyshow.main.model.BaseModel;
import com.bookmyshow.theatre.model.Theatre;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "cities", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"state_id", "name"})
})
@Data
public class City extends BaseModel {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "state_id", nullable = false)
    private State state;

    @Column(name = "name", nullable = false, length = 120)
    private String name;

    @Column(name = "city_code", nullable = false, length = 10)
    private String cityCode;

    @OneToMany(mappedBy = "city")
    private List<Theatre> theatreList;
}
