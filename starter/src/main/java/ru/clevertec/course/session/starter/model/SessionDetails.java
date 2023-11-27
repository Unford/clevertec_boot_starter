package ru.clevertec.course.session.starter.model;

import java.time.LocalDateTime;

public interface SessionDetails
{

    Long getId();
    String getLogin();
    LocalDateTime getCreateDateTime();

}
