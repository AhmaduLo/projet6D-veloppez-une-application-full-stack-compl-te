package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.dto.UserLoginDto;
import com.openclassrooms.mddapi.dto.UserRegisterDto;
import com.openclassrooms.mddapi.dto.UserUpdateDto;

import javax.servlet.http.HttpServletRequest;

public interface UserService {
    UserDto register(UserRegisterDto userRegisterDto);
    String login(UserLoginDto userLoginDto);
    UserDto updateCurrentUser(HttpServletRequest request, UserUpdateDto userUpdateDto);

}
