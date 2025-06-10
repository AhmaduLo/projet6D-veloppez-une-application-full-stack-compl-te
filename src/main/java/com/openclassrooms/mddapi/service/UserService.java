package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.dto.UserLoginDto;
import com.openclassrooms.mddapi.dto.UserRegisterDto;

public interface UserService {
    UserDto register(UserRegisterDto userRegisterDto);
    String login(UserLoginDto userLoginDto);
}
