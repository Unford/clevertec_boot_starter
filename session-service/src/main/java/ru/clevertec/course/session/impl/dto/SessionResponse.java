package ru.clevertec.course.session.impl.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class SessionResponse {
    private String id;
    private String login;
    private LocalDateTime createDateTime;
}
