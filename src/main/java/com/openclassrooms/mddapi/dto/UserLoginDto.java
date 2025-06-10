package com.openclassrooms.mddapi.dto;

import lombok.Data;

@Data
public class UserLoginDto {
    private String email;
    private String password;
}

