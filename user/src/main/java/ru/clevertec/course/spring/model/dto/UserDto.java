package ru.clevertec.course.spring.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;
import ru.clevertec.course.spring.model.validation.CreateValidation;

@Data
@Accessors(chain = true)
public class UserDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    @NotNull(groups = CreateValidation.class)
    @Size(max = 255)
    private String nickname;
    @NotNull(groups = CreateValidation.class)
    @Size(max = 255)
    private String name;
    @Email
    @NotNull(groups = CreateValidation.class)
    @Size(max = 255)
    private String email;

}
