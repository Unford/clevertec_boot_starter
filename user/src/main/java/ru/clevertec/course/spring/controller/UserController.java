package ru.clevertec.course.spring.controller;

import jakarta.validation.constraints.Positive;
import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.course.session.api.model.SessionDetails;
import ru.clevertec.course.session.starter.annotation.LoginParameter;
import ru.clevertec.course.session.starter.annotation.SessionManagement;
import ru.clevertec.course.session.starter.service.PropertyBlackListProvider;
import ru.clevertec.course.spring.model.dto.UserDto;
import ru.clevertec.course.spring.model.validation.CreateValidation;
import ru.clevertec.course.spring.service.UserService;
import ru.clevertec.course.spring.session.GatewayBlackListProvider;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
@Validated
@Slf4j
public class UserController {
    private final UserService userService;
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    @SessionManagement(blackList = "s", blackListProviders = GatewayBlackListProvider.class, includeDefaultProviders = false)
    public UserDto createUser(@RequestBody @Validated({CreateValidation.class, Default.class})
                              UserDto userDto, SessionDetails sessionDetails) {
        log.info("{}",sessionDetails);
        return userService.create(userDto);

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable("id") @Positive Long id) {
        userService.deleteById(id);
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<UserDto> getUsers() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public UserDto getUsers(@PathVariable("id") @Positive Long id) {
        return userService.findById(id);
    }



}
