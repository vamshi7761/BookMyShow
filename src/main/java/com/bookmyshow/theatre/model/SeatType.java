package com.bookmyshow.theatre.model;

import com.bookmyshow.main.model.BaseModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class SeatType extends BaseModel {

    @Column(nullable = false)
    private String type;
}