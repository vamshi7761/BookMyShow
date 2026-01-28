package com.bookmyshow.security.model;


import com.bookmyshow.main.model.BaseModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class ClientAuthConfig extends BaseModel {

    @Column(nullable = false, unique = true)
    private String clientId;

    @Column(nullable = false)
    private String clientSecretKey;

    @Column(nullable = false)
    private Integer tokenExpiryMillis;

}
