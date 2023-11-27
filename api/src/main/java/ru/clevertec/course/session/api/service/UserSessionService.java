package ru.clevertec.course.session.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.course.session.api.event.SessionSpoiledEventPublisher;
import ru.clevertec.course.session.api.model.domain.UserSession;
import ru.clevertec.course.session.api.model.dto.response.SessionResponse;
import ru.clevertec.course.session.api.model.mapper.SessionMapper;
import ru.clevertec.course.session.api.repository.UserSessionRepository;
import ru.clevertec.course.session.starter.model.SessionDetails;
import ru.clevertec.course.session.starter.service.SessionService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserSessionService implements SessionService {
    private final UserSessionRepository userSessionRepository;
    private final SessionSpoiledEventPublisher spoiledEventPublisher;
    private final SessionMapper mapper;

    @Override
    @Transactional
    public SessionDetails create(String login) {
        return userSessionRepository.save(new UserSession().setLogin(login));
    }

    @Override
    public Optional<SessionDetails> getSession(String login) {
        Optional<SessionDetails> details = userSessionRepository.findByLogin(login);

        return details.filter(this::isSessionSpoiled)
                .map(s -> {
                    spoiledEventPublisher.publish();
                    return Optional.<SessionDetails>empty();
                }).orElse(details);


    }

    private boolean isSessionSpoiled(SessionDetails userSession) {
        LocalDateTime now = LocalDate.now().atStartOfDay();
        return userSession.getCreateDateTime().isBefore(now);
    }

    @Transactional
    public int deleteAll() {
        return userSessionRepository.deleteByCreatedDateTimeLessThan(LocalDate.now().atStartOfDay());
    }

    public List<SessionResponse> findAll(){
        return userSessionRepository.findAll()
                .stream().map(mapper::toResponse).collect(Collectors.toList());
    }


}
