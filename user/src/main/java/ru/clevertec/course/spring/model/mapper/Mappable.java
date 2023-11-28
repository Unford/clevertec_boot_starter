package ru.clevertec.course.spring.model.mapper;

import java.util.Collection;
import java.util.List;

public interface Mappable<E, D> {
    D toDto(E entity);

    List<D> toDto(Collection<E> entities);

    E toEntity(D dto);


}
