package ru.clevertec.course.session.starter.cleaner;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import ru.clevertec.course.session.api.service.SessionService;

@RequiredArgsConstructor
@Slf4j
public class SessionCleaner {

    private final SessionService sessionService;

    @Scheduled(cron = "@daily")
    @EventListener(ApplicationReadyEvent.class)
    public void cleanSessions() {
        int deletedQuantity = sessionService.deleteAllSpoiled();
        log.info("Clean session processed {} sessions deleted", deletedQuantity);

    }

}
