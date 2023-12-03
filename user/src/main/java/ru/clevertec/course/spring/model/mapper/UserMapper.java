package ru.clevertec.course.spring.model.mapper;

import org.mapstruct.Mapper;
import ru.clevertec.course.spring.model.domain.User;
import ru.clevertec.course.spring.model.dto.UserDto;

import java.util.Collection;
import java.util.List;

@Mapper
public interface UserMapper {
    UserDto toDto(User entity);

    List<UserDto> toDto(Collection<User> entities);

    User toEntity(UserDto dto);
}
