package com.duvanlabrador.parking.Security;

import com.duvanlabrador.parking.DTO.UserDto;
import com.duvanlabrador.parking.Exception.UserWithSameUsernameAlreadyExists;
import com.duvanlabrador.parking.Service.Interface.IUserService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
@Configuration
public class SetupInitialUser implements ApplicationListener<ContextRefreshedEvent> {
    private boolean alreadySetup = false;

    private final IUserService userService;

    public SetupInitialUser(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent e) {
        if (alreadySetup) return;

        UserDto user = new UserDto(null,"admin","admin","admin", "admin@email.com", "admin", "true","ADMIN");
        try {
            userService.signUp(user);
        } catch (UserWithSameUsernameAlreadyExists ignored) {} catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        alreadySetup = true;
    }
}
