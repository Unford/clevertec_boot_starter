package ru.clevertec.course.session.starter.model;

import lombok.Data;
import lombok.experimental.Accessors;
import ru.clevertec.course.session.api.model.SessionDetails;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Accessors(chain = true)
public class DefaultSessionDetails implements SessionDetails {
    private String id;
    private String login;
    private LocalDateTime createDateTime;

    public DefaultSessionDetails(String id, String login) {
        this.id = id;
        this.login = login;
        this.createDateTime = LocalDateTime.now();
    }
}
