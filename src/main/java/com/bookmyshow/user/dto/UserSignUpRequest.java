package com.bookmyshow.user.dto;

import lombok.Data;

@Data
public class UserSignUpRequest {
    private String inputId;
    private String password;
    private String loginType;
    private String clientId;

}
