package ru.clevertec.course.session.api.model.dto.response;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class SessionResponse {
    private Long id;
    private String login;
    private LocalDateTime createDateTime;
}
