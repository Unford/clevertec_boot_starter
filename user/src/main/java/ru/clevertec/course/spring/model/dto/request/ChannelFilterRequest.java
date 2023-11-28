package ru.clevertec.course.spring.model.dto.request;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Data
@Accessors(chain = true)
public class ChannelFilterRequest {
    @Positive
    private int page = 1;
    @Positive
    private int size = 10;
    @Size(max = 255)
    private String title;
    @Size(max = 255)
    private String language;
    @Size(max = 255)
    private String category;

    public Pageable toPageable() {
        return PageRequest.of(page - 1, size);
    }
}
