package ru.clevertec.course.session.starter.service;

import ru.clevertec.course.session.api.model.SessionDetails;
import ru.clevertec.course.session.api.service.SessionService;
import ru.clevertec.course.session.api.model.DefaultSessionDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class MapSessionService implements SessionService {
    private Map<String, SessionDetails> map = new ConcurrentHashMap<>();


    @Override
    public SessionDetails create(String login) {
        DefaultSessionDetails sessionDetails = new DefaultSessionDetails(UUID.randomUUID().toString(), login);
        map.put(login, sessionDetails);
        return sessionDetails;
    }

    @Override
    public Optional<SessionDetails> getSession(String login) {
        return Optional.ofNullable(map.get(login));
    }

    @Override
    public int deleteAllSpoiled() {
        LocalDateTime now = LocalDate.now().atStartOfDay();
        int before = map.size();
        map = map.entrySet().stream()
                .filter(kv -> kv.getValue().getCreateDateTime().isBefore(now))
                .collect(Collectors.toConcurrentMap(Map.Entry::getKey, Map.Entry::getValue));
        return before - map.size();
    }
}
