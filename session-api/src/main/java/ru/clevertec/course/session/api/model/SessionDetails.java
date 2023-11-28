package ru.clevertec.course.session.api.model;

import java.time.LocalDateTime;
import java.util.UUID;

public interface SessionDetails
{

    String getId();
    String getLogin();
    LocalDateTime getCreateDateTime();

}
