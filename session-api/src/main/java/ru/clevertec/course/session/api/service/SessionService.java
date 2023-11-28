package ru.clevertec.course.session.api.service;



import ru.clevertec.course.session.api.model.SessionDetails;

import java.util.Optional;

public interface SessionService {
    SessionDetails create(String login);
    Optional<SessionDetails> getSession(String login);

    int deleteAllSpoiled();
}
