package ru.clevertec.course.spring.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import ru.clevertec.course.session.api.model.SessionDetails;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class SessionResponse implements SessionDetails {
    private String id;
    private String login;
    private LocalDateTime createDateTime;
}
