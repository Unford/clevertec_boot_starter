package ru.clevertec.course.session.impl.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class SessionRequest {
    @NotNull
    @Size(max = 255)
    private String login;
}
