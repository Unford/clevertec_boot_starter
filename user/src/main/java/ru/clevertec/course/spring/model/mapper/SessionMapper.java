package ru.clevertec.course.spring.model.mapper;

import org.mapstruct.Mapper;
import ru.clevertec.course.session.api.model.DefaultSessionDetails;
import ru.clevertec.course.spring.model.domain.UserSession;

@Mapper
public interface SessionMapper {
    DefaultSessionDetails toSessionDetails(UserSession userSession);
}
