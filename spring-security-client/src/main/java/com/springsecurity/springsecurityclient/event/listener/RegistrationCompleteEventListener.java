package com.springsecurity.springsecurityclient.event.listener;

import com.springsecurity.springsecurityclient.dto.UserDto;
import com.springsecurity.springsecurityclient.event.RegistrationCompleteEvent;
import com.springsecurity.springsecurityclient.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {

    @Autowired
    private UserService userService;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        // Create the verification token for the user with link " link to reward the user back to application"
        UserDto userDto = event.getUserDto();
        String token = UUID.randomUUID().toString();

        userService.saveVerificationTokenForUser(token,userDto);
        // send Mail to User
        String url = event.getApplicationUrl()
                + "/verifyRegistration?token="
                + token;

        //sendVerificationEmail()
        log.info("click the link to verify your account : {}", url);
    }
}
