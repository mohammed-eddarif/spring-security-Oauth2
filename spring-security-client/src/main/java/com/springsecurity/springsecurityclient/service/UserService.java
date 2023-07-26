package com.springsecurity.springsecurityclient.service;

import com.springsecurity.springsecurityclient.dto.UserDto;
import com.springsecurity.springsecurityclient.entity.User;

public interface UserService {
    UserDto registerUser(UserDto userDto);

    void saveVerificationTokenForUser(String token, UserDto userDto);

    String validateVerificationToken(String token);
}
