package ru.clevertec.course.session.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.course.session.api.model.dto.request.LoginRequest;
import ru.clevertec.course.session.api.model.dto.response.SessionResponse;
import ru.clevertec.course.session.api.service.AuthService;

@RestController
@Validated
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;


    @PostMapping
    public SessionResponse login(@RequestBody @Validated LoginRequest loginRequest) {
        return authService.getSession(loginRequest.getLogin(), null);
    }

    @PostMapping("/i")
    public SessionResponse loginInterface(@RequestBody @Validated LoginRequest loginRequest) {
        return authService.getSession(loginRequest, null);
    }


}
