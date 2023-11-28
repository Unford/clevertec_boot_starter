package ru.clevertec.course.spring.service;

import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.clevertec.course.spring.exception.ResourceAlreadyExists;
import ru.clevertec.course.spring.exception.ResourceNotFoundException;
import ru.clevertec.course.spring.model.domain.Category;
import ru.clevertec.course.spring.model.domain.Channel;
import ru.clevertec.course.spring.model.dto.request.ChannelFilterRequest;
import ru.clevertec.course.spring.model.dto.request.ChannelPatchRequest;
import ru.clevertec.course.spring.model.dto.request.ChannelRequest;
import ru.clevertec.course.spring.model.dto.response.ChannelFullResponse;
import ru.clevertec.course.spring.model.dto.response.ChannelShortResponse;
import ru.clevertec.course.spring.model.mapper.ChannelMapper;
import ru.clevertec.course.spring.repository.CategoryRepository;
import ru.clevertec.course.spring.repository.ChannelRepository;
import ru.clevertec.course.spring.repository.UserRepository;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChannelService {
    private final ImageService imageService;

    private final UserRepository userRepository;
    private final ChannelRepository channelRepository;
    private final CategoryRepository categoryRepository;

    private final ChannelMapper channelMapper;

    @Transactional
    public ChannelFullResponse create(ChannelRequest channelRequest) {
        channelRepository.findChannelByTitle(channelRequest.getTitle())
                .ifPresent(c -> {
                    throw new ResourceAlreadyExists("Channel title '%s' is already taken"
                            .formatted(channelRequest.getTitle()));
                });

        Channel channel = userRepository.findById(channelRequest.getAuthor().getId())
                .map(u -> channelMapper.toEntity(channelRequest).setAuthor(u))
                .map(u -> u.setCategory(prepareCategory(u.getCategory().getTitle())))
                .map(channelRepository::save)
                .map(ch -> saveImage(channelRequest.getFile(), ch))
                .orElseThrow(() -> new ResourceNotFoundException("User associated with '%s' id is not found"
                        .formatted(channelRequest.getAuthor().getId())));


        return channelMapper.toFullResponse(channel);
    }

    private Category prepareCategory(String category) {
        return category == null ? null :
                categoryRepository.findByTitle(category).orElse(new Category().setTitle(category));
    }

    @Transactional
    public ChannelFullResponse update(ChannelPatchRequest channelRequest) {
        channelRepository.findChannelByTitle(channelRequest.getTitle())
                .ifPresent(c -> {
                    throw new ResourceAlreadyExists("Channel title '%s' is already taken"
                            .formatted(channelRequest.getTitle()));
                });
        return channelRepository.findById(channelRequest.getId())
                .map(c -> channelMapper.updateFromDto(channelRequest, c))
                .map(c -> channelRequest.getCategory() != null ?
                        c.setCategory(prepareCategory(channelRequest.getCategory())) : c)
                .map(channelRepository::save)
                .map(ch -> saveImage(channelRequest.getFile(), ch))
                .map(channelMapper::toFullResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Channel associated with %s id is not found"
                        .formatted(channelRequest.getId())));
    }

    private Channel saveImage(MultipartFile multipartFile, Channel ch) {
        if (Objects.nonNull(multipartFile)) {
            String avatar = imageService.upload(multipartFile, ch.getId().toString());
            ch.setImage(avatar);
        }
        return ch;
    }

    public Page<ChannelShortResponse> findAllFiltered(ChannelFilterRequest cfr) {
        return channelRepository.findAllFiltered(cfr.getTitle(), cfr.getLanguage(),
                        cfr.getCategory(), cfr.toPageable())
                .map(channelMapper::toShortResponse);


    }

    public byte[] findChannelAvatar(Long id) {
        Channel channel = channelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Channel associated with %s id is not found"
                        .formatted(id)));
        return imageService.getImage(channel.getImage());
    }

    public ChannelFullResponse findById(Long id) {
        Channel channel = channelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Channel associated with %s id is not found"
                        .formatted(id)));
        String encodedImage = imageService.getEncodedImage(channel.getImage());
        return channelMapper.toFullResponse(channel)
                .setImage(encodedImage)
                .setSubscriberCount(Hibernate.size(channel.getSubscribers()));
    }


}
