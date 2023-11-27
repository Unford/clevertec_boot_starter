package ru.clevertec.course.session.api.model.mapper;

import org.mapstruct.Mapper;
import ru.clevertec.course.session.api.model.dto.response.SessionResponse;
import ru.clevertec.course.session.starter.model.SessionDetails;

@Mapper
public interface SessionMapper {
    SessionResponse toResponse(SessionDetails userSession);
}
