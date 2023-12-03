package ru.clevertec.course.spring.gateway;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.course.spring.model.dto.LoginDto;
import ru.clevertec.course.spring.model.dto.SessionResponse;

@FeignClient(name = "sessionGateway",
        url = "${clevertec.session.url}", configuration = CustomErrorDecoder.class)
public interface SessionGateway {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    SessionResponse findByLogin(@RequestParam String login);

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    SessionResponse create(@RequestBody LoginDto login);

    @DeleteMapping(value = "/clean", produces = MediaType.APPLICATION_JSON_VALUE)
    int clean();
}
