package com.springsecurity.springsecurityclient.service;

import com.springsecurity.springsecurityclient.dto.UserDto;
import com.springsecurity.springsecurityclient.entity.User;
import com.springsecurity.springsecurityclient.entity.VerificationToken;

import java.util.Optional;

public interface UserService {
    UserDto registerUser(UserDto userDto);

    void saveVerificationTokenForUser(String token, UserDto userDto);

    String validateVerificationToken(String token);
    String validatePasswordResetToken(String token);

    VerificationToken generateNewVerificationToken(String oldToken);

    User findUserByEmail(String email);

    void createPasswordResetTokenForUser(User user, String token);

    Optional<User> getUserByPasswordResetToken(String token);

    void changePassword(User user, String newPassword);

    boolean checkValidOldPassword(User user, String oldPassword);
}
