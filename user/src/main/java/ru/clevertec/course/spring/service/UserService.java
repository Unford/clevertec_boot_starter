package ru.clevertec.course.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.course.spring.exception.ResourceAlreadyExists;
import ru.clevertec.course.spring.exception.ResourceNotFoundException;
import ru.clevertec.course.spring.model.dto.UserDto;
import ru.clevertec.course.spring.model.mapper.UserMapper;
import ru.clevertec.course.spring.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper mapper;

    @Transactional
    public UserDto create(UserDto userDto) {
        checkUniqueLoginAndEmail(userDto.getLogin(), userDto.getEmail());
        return mapper.toDto(userRepository.save(mapper.toEntity(userDto)));
    }

    private void checkUniqueLoginAndEmail(String nickname, String email) {
        userRepository.findUserByLogin(nickname).ifPresent(u -> {
            throw new ResourceAlreadyExists("User nickname '%s' is already taken".formatted(nickname));
        });
        userRepository.findUserByEmail(email).ifPresent(u -> {
            throw new ResourceAlreadyExists("User email '%s' is already taken".formatted(email));
        });
    }


    public List<UserDto> findAll() {
        return mapper.toDto(userRepository.findAll());
    }



    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public UserDto findById(Long id) {
        return mapper.toDto(userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id %s not found".formatted(id))));
    }
}
