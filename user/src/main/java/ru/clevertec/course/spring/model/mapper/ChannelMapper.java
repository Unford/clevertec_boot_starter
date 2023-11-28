package ru.clevertec.course.spring.model.mapper;

import org.mapstruct.*;
import ru.clevertec.course.spring.model.domain.Channel;
import ru.clevertec.course.spring.model.domain.projection.ChannelWithSubCount;
import ru.clevertec.course.spring.model.dto.request.ChannelPatchRequest;
import ru.clevertec.course.spring.model.dto.request.ChannelRequest;
import ru.clevertec.course.spring.model.dto.response.ChannelFullResponse;
import ru.clevertec.course.spring.model.dto.response.ChannelShortResponse;

@Mapper
public interface ChannelMapper extends Mappable<Channel, ChannelRequest> {
    @Mapping(target = "category", source = "category.title")
    @Mapping(target = "subscriberCount", ignore = true)
    ChannelFullResponse toFullResponse(Channel channel);

    @Override
    @Mapping(target = "category", source = "category.title")
    ChannelRequest toDto(Channel entity);

    @Override
    @Mapping(target = "category.title", source = "category")
    Channel toEntity(ChannelRequest dto);


    ChannelShortResponse toShortResponse(ChannelWithSubCount withSubCount);
    @Mapping(target = "category.title", source = "category", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Channel updateFromDto(ChannelPatchRequest channelRequest, @MappingTarget Channel c);
}
