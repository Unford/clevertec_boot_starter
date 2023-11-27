package ru.clevertec.course.session.api.event;

import org.springframework.context.ApplicationEvent;

public class SessionSpoiledEvent extends ApplicationEvent {
    public SessionSpoiledEvent(Object source) {
        super(source);
    }
}
