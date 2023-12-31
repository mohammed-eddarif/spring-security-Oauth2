package com.springsecurity.springsecurityclient.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRegistrationRequest {

    private String firstName;
    private String lastName;
    private String email;

    private String password;
    private String matchingPassword;

    // you can add the logic to validate the password and matching password before saving the data
}
