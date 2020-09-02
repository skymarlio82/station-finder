package com.whiz.app.boot.domain.listener;

import com.whiz.app.boot.domain.model.UserProfile;
import com.whiz.app.boot.domain.model.UserUpdateEvent;
import com.whiz.app.boot.domain.service.UserProfileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserUpdateListener {
    private final UserProfileService userProfileService;

    public UserUpdateListener(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @Async
    @EventListener
    public void handleEventContext(UserUpdateEvent uue) {
        log.info("====> handleEventContext of UserUpdateEvent is started ...");
        UserProfile up = uue.getUserProfile();
        log.debug("====> user object from event = {}", up);
        userProfileService.saveNewUser(up);
    }
}