package ru.clevertec.course.session.impl.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import ru.clevertec.course.session.impl.dto.SessionRequest;
import ru.clevertec.course.session.impl.dto.SessionResponse;
import ru.clevertec.course.session.impl.service.SessionService;

@RestController
@RequestMapping("api/v1/sessions")
@RequiredArgsConstructor
@Validated
@Slf4j
public class SessionController {
    private final SessionService sessionService;

    @GetMapping
    public SessionResponse findByLogin(@RequestParam @NotBlank @Size(max = 255) String login) {
       return sessionService.getSession(login);
    }

    @PostMapping
    public SessionResponse createSession(@RequestBody @Valid SessionRequest request) {
        return sessionService.create(request.getLogin());
    }

    @DeleteMapping("/clean")
    public int clean() {
        return sessionService.deleteAllSpoiled();
    }


}
