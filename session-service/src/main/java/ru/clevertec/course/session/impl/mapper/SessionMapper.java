package ru.clevertec.course.session.impl.mapper;


import org.mapstruct.Mapper;
import ru.clevertec.course.session.impl.dto.SessionResponse;
import ru.clevertec.course.session.impl.model.UserSession;

@Mapper
public interface SessionMapper {
    SessionResponse toSessionResponse(UserSession userSession);

}
