package com.plml.pplatform.users.signOnProcess;

import com.plml.pplatform.users.ApplicationUser;
import org.springframework.context.ApplicationEvent;

import javax.validation.Valid;

public class OnRegistrationCompleteEvent extends ApplicationEvent {

    private ApplicationUser applicationUser;

    public OnRegistrationCompleteEvent(@Valid ApplicationUser user) {
        super(user);

        this.applicationUser = user;
    }

    public ApplicationUser getApplicationUser() {
        return applicationUser;
    }

    public void setApplicationUser(ApplicationUser applicationUser) {
        this.applicationUser = applicationUser;
    }
}
