package ru.clevertec.course.session.api.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;
import ru.clevertec.course.session.starter.model.LoginProvider;

@Data
@Accessors(chain = true)
public class LoginRequest implements LoginProvider {
    @NotBlank
    @Size(max = 50)
    private String login;
}
