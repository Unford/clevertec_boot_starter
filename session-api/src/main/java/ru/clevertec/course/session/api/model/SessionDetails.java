package ru.clevertec.course.session.api.model;

import java.time.LocalDateTime;

public interface SessionDetails
{

    String getId();
    String getLogin();
    LocalDateTime getCreateDateTime();

}
