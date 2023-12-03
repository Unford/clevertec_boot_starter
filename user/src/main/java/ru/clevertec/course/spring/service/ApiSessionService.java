package ru.clevertec.course.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import ru.clevertec.course.session.api.model.SessionDetails;
import ru.clevertec.course.session.api.service.SessionService;
import ru.clevertec.course.spring.exception.ResourceNotFoundException;
import ru.clevertec.course.spring.gateway.SessionGateway;
import ru.clevertec.course.spring.model.dto.LoginDto;

import java.util.Optional;

@Profile("api")
@RequiredArgsConstructor
public class ApiSessionService implements SessionService {
    private final SessionGateway sessionGateway;

    @Override
    public SessionDetails create(String login) {
        return sessionGateway.create(new LoginDto().setLogin(login));
    }

    @Override
    public Optional<SessionDetails> getSession(String login) {
        try {
            return Optional.ofNullable(sessionGateway.findByLogin(login));
        } catch (ResourceNotFoundException e) {
            return Optional.empty();
        }
    }
}
