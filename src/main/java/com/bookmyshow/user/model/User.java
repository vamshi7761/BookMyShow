package com.bookmyshow.user.model;


import com.bookmyshow.main.model.BaseModel;
import com.bookmyshow.booking.model.Booking;
import com.bookmyshow.role.model.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity(name="bms_user")
@Getter
@Setter
public class User extends BaseModel {

    private String name;

    private String email;

    private String phone;

    @OneToMany(mappedBy = "user")
    private List<Booking> bookings;

    @Column(nullable = false)
    private String password;

    @ManyToMany
    private List<Role> role;

}