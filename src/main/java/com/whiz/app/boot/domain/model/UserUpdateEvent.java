package com.whiz.app.boot.domain.model;

import org.springframework.context.ApplicationEvent;

public class UserUpdateEvent extends ApplicationEvent {
    private UserProfile userProfile;

    public UserUpdateEvent(Object source, UserProfile userProfile) {
        super(source);
        this.userProfile = userProfile;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }
}