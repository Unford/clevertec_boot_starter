package ru.clevertec.course.spring.model.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;
import ru.clevertec.course.spring.model.validation.CreateValidation;


@Data
@Accessors(chain = true)
public class ChannelRequest {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotNull(groups = CreateValidation.class)
    @Size(max = 255)
    private String title;

    @Size(max = 255)
    private String description;

    @NotNull(groups = CreateValidation.class)
    private @Valid AuthorDto author;

    @Size(max = 255)
    private String category;

    @Size(max = 255)
    private String language;

    @JsonIgnore
    private MultipartFile file;


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AuthorDto {
        @NotNull(groups = CreateValidation.class)
        private Long id;

    }
}