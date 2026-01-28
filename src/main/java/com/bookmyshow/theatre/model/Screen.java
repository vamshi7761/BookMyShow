package com.bookmyshow.theatre.model;


import com.bookmyshow.main.model.BaseModel;
import com.bookmyshow.theatre.enums.TheatreStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
import com.bookmyshow.theatre.enums.Feature;


@Getter
@Setter
@Entity
@Data
public class Screen extends BaseModel {

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.ORDINAL)
    @ElementCollection
    private List<Feature> features;

    @OneToMany(mappedBy = "screenId", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Seat> seats;

    @ManyToOne
    @JoinColumn(name = "theatre_id", nullable = false)
    private Theatre theatre;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status", nullable = false)
    private TheatreStatus status;
}