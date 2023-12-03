package ru.clevertec.course.session.impl.cleaner;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.clevertec.course.session.impl.service.SessionService;

@RequiredArgsConstructor
@Slf4j
@Component
public class SessionCleaner {

    private final SessionService sessionService;

    @Scheduled(cron = "@daily")
    @EventListener(ApplicationReadyEvent.class)
    public void cleanSessions() {
        int deletedQuantity = sessionService.deleteAllSpoiled();
        log.info("Clean session processed {} sessions deleted", deletedQuantity);

    }

}
