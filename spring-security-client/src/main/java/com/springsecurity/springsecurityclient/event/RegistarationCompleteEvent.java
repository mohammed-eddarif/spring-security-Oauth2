package com.springsecurity.springsecurityclient.event;

import com.springsecurity.springsecurityclient.dto.UserDto;
import com.springsecurity.springsecurityclient.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class RegistarationCompleteEvent extends ApplicationEvent {

    private UserDto userDto;

    //the url we will send to user to verify his account
    private String applicationUrl;
    public RegistarationCompleteEvent(UserDto userDto, String applicationUrl) {
        super(userDto);
        this.userDto = userDto;
        this.applicationUrl=applicationUrl;
    }
}
