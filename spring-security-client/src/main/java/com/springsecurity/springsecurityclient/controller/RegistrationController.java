package com.springsecurity.springsecurityclient.controller;

import com.springsecurity.springsecurityclient.dto.UserDto;
import com.springsecurity.springsecurityclient.dto.UserRegistrationRequest;
import com.springsecurity.springsecurityclient.dto.UserRegistrationResponse;
import com.springsecurity.springsecurityclient.entity.User;
import com.springsecurity.springsecurityclient.entity.VerificationToken;
import com.springsecurity.springsecurityclient.event.RegistarationCompleteEvent;
import com.springsecurity.springsecurityclient.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@Slf4j
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @PostMapping("/register")
    public ResponseEntity<UserRegistrationResponse> registerUser(@RequestBody UserRegistrationRequest userRegistrationRequest ,final HttpServletRequest request) {
        try {
            UserDto userDto = UserDto.builder()
                    .firstName(userRegistrationRequest.getFirstName())
                    .lastName(userRegistrationRequest.getLastName())
                    .email(userRegistrationRequest.getEmail())
                    .password(userRegistrationRequest.getPassword())
                    .build();

            UserDto userDto1 = userService.registerUser(userDto);

            UserRegistrationResponse userRegistrationResponse = UserRegistrationResponse.builder()
                    .firstName(userDto1.getFirstName())
                    .lastName(userDto1.getLastName())
                    .email(userDto1.getEmail())
                    .build();

            publisher.publishEvent(new RegistarationCompleteEvent(
                    userDto1,
                    applicationUrl(request)
            ));

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(userRegistrationResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @GetMapping("/verifyRegistration")
    public String verifyRegistration(@RequestParam("token") String token){
        String result = userService.validateVerificationToken(token);
        if(result.equalsIgnoreCase("valid")) return "User Verifies Successfully";
        return "Bad User";
    }

    @GetMapping("/resendVerifyToken")
    public String resendVerificationToken(@RequestParam("token") String oldToken,
                                          HttpServletRequest request){
        VerificationToken verificationToken = userService.generateNewVerificationToken(oldToken);
        User user = verificationToken.getUser();
        resendVerificationTokenEmail(user, applicationUrl(request), verificationToken);
        return "Verification link sent";
    }

    private void resendVerificationTokenEmail(User user, String applicationUrl, VerificationToken verificationToken) {
        String url = applicationUrl
                + "/verifyRegistration?token="
                + verificationToken.getToken();

        //sendVerificationEmail
        log.info("click the link to verify your account : {} ", url);
    }

    private String applicationUrl(HttpServletRequest request) {
        return "http://"
                + request.getServerName()
                + ":"
                + request.getServerPort()
                + request.getContextPath();
    }


}
