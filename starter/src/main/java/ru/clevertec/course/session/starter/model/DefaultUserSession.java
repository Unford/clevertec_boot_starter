package ru.clevertec.course.session.starter.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class DefaultUserSession implements UserSession {
    private Long id;
    private String login;
    private LocalDateTime createdDateTime;
}
