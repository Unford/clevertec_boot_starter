package ru.clevertec.course.spring.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import ru.clevertec.course.session.api.model.LoginProvider;

@Data
@Accessors(chain = true)
public class LoginDto implements LoginProvider {
    private String login;
}
