package ru.clevertec.course.session.impl.service;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.course.session.api.model.SessionDetails;
import ru.clevertec.course.session.api.service.SessionService;
import ru.clevertec.course.session.impl.model.UserSession;
import ru.clevertec.course.session.impl.repository.UserSessionRepository;

import java.time.LocalDate;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
public class H2SessionService implements SessionService {
    private final UserSessionRepository userSessionRepository;

    @Override
    @Transactional
    public SessionDetails create(String login) {
        return userSessionRepository.save(new UserSession().setLogin(login));
    }

    @Override
    public Optional<SessionDetails> getSession(String login) {
        return userSessionRepository.findByLogin(login);


    }

    @Override
    @Transactional
    public int deleteAllSpoiled() {
        return userSessionRepository.deleteByCreatedDateTimeLessThan(LocalDate.now().atStartOfDay());
    }




}
