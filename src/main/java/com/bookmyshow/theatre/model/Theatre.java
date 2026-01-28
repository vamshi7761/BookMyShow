package com.bookmyshow.theatre.model;

import com.bookmyshow.location.model.City;
import com.bookmyshow.main.model.BaseModel;
import com.bookmyshow.theatre.enums.TheatreStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Theatre extends BaseModel {

    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    @Column(nullable = false)
    private String theatreName;

    @Column(nullable = false)
    private String address;

    @OneToMany(mappedBy = "theatre", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Screen> screens;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status", nullable = false)
    private TheatreStatus status;

}
