package com.bookmyshow.moviecatalog.model;


import com.bookmyshow.main.model.BaseModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.bookmyshow.moviecatalog.enums.Language;

import java.util.List;

@Getter
@Setter
@Entity
public class Movie extends BaseModel {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String genre;

    @Column(name = "run_time_mins", nullable = false)
    private int run_time_mins;

    @ElementCollection
    private List<String> actors;

    @Enumerated(EnumType.ORDINAL)
    @ElementCollection
    private List<Language> languages;

}
