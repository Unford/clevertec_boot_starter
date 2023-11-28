package ru.clevertec.course.session.api.model;

import java.time.LocalDateTime;

public interface SessionDetails
{

    Long getId();
    String getLogin();
    LocalDateTime getCreateDateTime();

}
