package ru.clevertec.course.session.api.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SessionSpoiledEventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    public void publish() {
        SessionSpoiledEvent sessionSpoiledEvent = new SessionSpoiledEvent(this);
        applicationEventPublisher.publishEvent(sessionSpoiledEvent);
    }
}
