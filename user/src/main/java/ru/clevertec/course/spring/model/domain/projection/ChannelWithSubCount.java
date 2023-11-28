package ru.clevertec.course.spring.model.domain.projection;

public interface ChannelWithSubCount {
    String getTitle();
    String getImage();
    String getCategory();
    String getLanguage();
    Long getSubscriberCount();
}
