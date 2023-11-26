package ru.clevertec.course.session.starter.model;

import java.time.LocalDateTime;

public interface UserSession {
    Long getId();
    String getLogin();
    LocalDateTime getCreatedDateTime();

}
