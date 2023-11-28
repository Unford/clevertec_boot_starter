package ru.clevertec.course.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.course.spring.exception.ResourceAlreadyExists;
import ru.clevertec.course.spring.exception.ResourceNotFoundException;
import ru.clevertec.course.spring.model.domain.projection.ChannelTitleOnly;
import ru.clevertec.course.spring.model.dto.UserDto;
import ru.clevertec.course.spring.model.mapper.UserMapper;
import ru.clevertec.course.spring.repository.ChannelRepository;
import ru.clevertec.course.spring.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final ChannelRepository channelRepository;
    private final UserMapper mapper;

    @Transactional
    public UserDto create(UserDto userDto) {
        checkUniqueNicknameAndEmail(userDto.getNickname(), userDto.getEmail());
        return mapper.toDto(userRepository.save(mapper.toEntity(userDto)));
    }

    private void checkUniqueNicknameAndEmail(String nickname, String email) {
        userRepository.findUserByNickname(nickname).ifPresent(u -> {
            throw new ResourceAlreadyExists("User nickname '%s' is already taken".formatted(nickname));
        });
        userRepository.findUserByEmail(email).ifPresent(u -> {
            throw new ResourceAlreadyExists("User email '%s' is already taken".formatted(email));
        });
    }

    @Transactional
    public UserDto update(UserDto userDto) {
        checkUniqueNicknameAndEmail(userDto.getNickname(), userDto.getEmail());
        return userRepository.findById(userDto.getId())
                .map(u -> mapper.updateFromDto(userDto, u))
                .map(userRepository::save)
                .map(mapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("User associated with %s id is not found"
                        .formatted(userDto.getId())));
    }

    public List<UserDto> findAll() {
        return mapper.toDto(userRepository.findAll());
    }

    public List<String> findAllSubscriptionsNamesById(Long id) {
        userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User associated with %s id is not found"
                        .formatted(id)));
        return channelRepository.findBySubscribersId(id).stream().map(ChannelTitleOnly::getTitle).toList();
    }
}
