package ru.clevertec.course.spring.model.dto.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import ru.clevertec.course.spring.model.dto.UserDto;

import java.time.LocalDate;

@Accessors(chain = true)
@Data
@EqualsAndHashCode(callSuper = true)
public class ChannelFullResponse extends ChannelShortResponse {
    private String description;
    private UserDto author;
    private LocalDate createDate;

    @Override
    public ChannelFullResponse setTitle(String title) {
        super.setTitle(title);
        return this;
    }

    @Override
    public ChannelFullResponse setImage(String image) {
        super.setImage(image);
        return this;
    }

    @Override
    public ChannelFullResponse setSubscriberCount(long subscriberCount) {
        super.setSubscriberCount(subscriberCount);
        return this;
    }

    @Override
    public ChannelFullResponse setCategory(String category) {
        super.setCategory(category);
        return this;

    }

    @Override
    public ChannelFullResponse setLanguage(String language) {
        super.setLanguage(language);
        return this;

    }
}
