package ru.clevertec.course.session.impl.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.course.session.impl.dto.SessionResponse;
import ru.clevertec.course.session.impl.exception.ResourceNotFoundException;
import ru.clevertec.course.session.impl.mapper.SessionMapper;
import ru.clevertec.course.session.impl.model.UserSession;
import ru.clevertec.course.session.impl.repository.SessionRepository;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SessionService {
    private final SessionRepository sessionRepository;
    private final SessionMapper mapper;

    @Transactional
    public SessionResponse create(String login) {
        return mapper.toSessionResponse(sessionRepository.save(new UserSession().setLogin(login)));
    }

    public SessionResponse getSession(String login) {
        return sessionRepository.findByLogin(login)
                .map(mapper::toSessionResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Login %s not found".formatted(login)));
    }

    @Transactional
    public int deleteAllSpoiled() {
        return sessionRepository.deleteByCreatedDateTimeLessThan(LocalDate.now().atStartOfDay());
    }
}
