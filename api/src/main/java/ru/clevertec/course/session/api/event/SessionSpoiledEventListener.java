package ru.clevertec.course.session.api.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.clevertec.course.session.api.service.UserSessionService;

@Component
@RequiredArgsConstructor
@Slf4j
public class SessionSpoiledEventListener {
    private final UserSessionService sessionService;
    @EventListener
    public void handleEvent(SessionSpoiledEvent event) {
        int deletedQuantity = sessionService.deleteAll();
        log.info("Clean session event handled {} sessions deleted", deletedQuantity);

    }
}
