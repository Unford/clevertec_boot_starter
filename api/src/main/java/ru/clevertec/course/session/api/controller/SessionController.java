package ru.clevertec.course.session.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.course.session.api.model.dto.response.SessionResponse;
import ru.clevertec.course.session.api.service.UserSessionService;

import java.util.List;

@RestController
@Validated
@RequestMapping("api/v1/sessions")
@RequiredArgsConstructor
public class SessionController {
    private final UserSessionService userSessionService;

    @GetMapping
    public List<SessionResponse> findAll() {
        return userSessionService.findAll();
    }

}
