package ru.clevertec.course.session.starter.service;

import ru.clevertec.course.session.starter.model.SessionDetails;

import java.util.Optional;

public interface SessionService {
    SessionDetails create(String login);
    Optional<SessionDetails> getSession(String login);
}
