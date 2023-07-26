package com.springsecurity.springsecurityclient.service.impl;

import com.springsecurity.springsecurityclient.dto.UserDto;
import com.springsecurity.springsecurityclient.entity.User;
import com.springsecurity.springsecurityclient.entity.VerificationToken;
import com.springsecurity.springsecurityclient.event.RegistarationCompleteEvent;
import com.springsecurity.springsecurityclient.repository.UserRepository;
import com.springsecurity.springsecurityclient.repository.VerificationTokenRepository;
import com.springsecurity.springsecurityclient.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    VerificationTokenRepository verificationTokenRepository;

    @Autowired
    PasswordEncoder passwordEncoder;
    @Override
    public UserDto registerUser(UserDto userDto) {
        User user = User.builder()
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .role("USER")
                .build();

        User user1 = userRepository.save(user);

        UserDto userDto1 = UserDto.builder()
                .id(user1.getId())
                .firstName(user1.getFirstName())
                .lastName(user1.getLastName())
                .email(user1.getEmail())
                .password(user1.getPassword())
                .role(user1.getRole())
                .build();


        return userDto1;
    }

    @Override
    public void saveVerificationTokenForUser(String token, UserDto userDto) {
        User user = User
                .builder()
                .id(userDto.getId())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .role(userDto.getRole())
                .build();
        VerificationToken verificationToken = new VerificationToken(user, token);
        verificationTokenRepository.save(verificationToken);
    }
}
