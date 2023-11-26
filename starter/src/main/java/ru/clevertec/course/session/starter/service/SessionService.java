package ru.clevertec.course.session.starter.service;

import ru.clevertec.course.session.starter.model.UserSession;

import java.util.Optional;

public interface SessionService {
    UserSession create(String login);
    Optional<UserSession> getSession(String login);
}
