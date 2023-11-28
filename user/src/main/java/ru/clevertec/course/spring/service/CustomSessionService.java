package ru.clevertec.course.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.course.session.api.model.SessionDetails;
import ru.clevertec.course.session.api.service.SessionService;
import ru.clevertec.course.spring.model.domain.UserSession;
import ru.clevertec.course.spring.model.mapper.SessionMapper;
import ru.clevertec.course.spring.repository.UserRepository;
import ru.clevertec.course.spring.repository.UserSessionRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomSessionService implements SessionService {


    private final UserSessionRepository repository;
    private final SessionMapper mapper;

    @Override
    @Transactional
    public SessionDetails create(String login) {
        return mapper.toSessionDetails(repository.save(new UserSession().setLogin(login)));
    }

    @Override
    public Optional<SessionDetails> getSession(String login) {
        return repository.findByLogin(login).map(mapper::toSessionDetails);
    }

    @Override
    @Transactional
    public int deleteAllSpoiled() {
        return repository.deleteByCreatedDateTimeLessThan(LocalDate.now().atStartOfDay());
    }
}
