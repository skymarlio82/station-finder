package com.whiz.app.boot.infrastructure.event;

import com.whiz.app.boot.domain.model.UserProfile;
import com.whiz.app.boot.domain.model.UserUpdateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserUpdateEventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    public UserUpdateEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void doPublishEvent(final UserProfile userProfile) {
        log.info("====> start to publish an event of UserUpdateEvent ...");
        UserUpdateEvent userUpdateEvent = new UserUpdateEvent(this, userProfile);
        applicationEventPublisher.publishEvent(userUpdateEvent);
    }
}