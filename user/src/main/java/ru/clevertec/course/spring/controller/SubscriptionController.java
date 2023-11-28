package ru.clevertec.course.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.course.spring.model.dto.request.SubscriptionRequest;
import ru.clevertec.course.spring.service.SubscriptionService;

@RestController
@RequestMapping("api/v1/subscriptions")
@RequiredArgsConstructor
@Validated
public class SubscriptionController {
    private final SubscriptionService service;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public SubscriptionRequest subscribeUserToChannel(@RequestBody @Validated SubscriptionRequest request) {
        return service.subscribe(request);
    }

    @DeleteMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void unsubscribeUserFromChannel(@RequestBody @Validated SubscriptionRequest request) {
        service.unsubscribe(request);
    }
}
