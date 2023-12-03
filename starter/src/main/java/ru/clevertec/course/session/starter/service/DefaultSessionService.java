package ru.clevertec.course.session.starter.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.clevertec.course.session.api.model.DefaultLoginProvider;
import ru.clevertec.course.session.api.model.DefaultSessionDetails;
import ru.clevertec.course.session.api.model.SessionDetails;
import ru.clevertec.course.session.api.service.SessionService;
import ru.clevertec.course.session.starter.exception.SessionServiceException;
import ru.clevertec.course.session.starter.property.SessionServiceProperties;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class DefaultSessionService implements SessionService {
    private static final String SUFFIX = "/";
    private static final String LOGIN = "login";

    private final RestTemplate restTemplate;
    private final SessionServiceProperties serviceProperties;

    private String createUrl;
    private String findUrl;

    @PostConstruct
    private void construct() {
        createUrl = getUrl(serviceProperties.getCreatePath());
        findUrl = getUrl(serviceProperties.getFindPath());
    }

    @Override
    public SessionDetails create(String login) {
        ResponseEntity<DefaultSessionDetails> response = restTemplate.postForEntity(createUrl,
                new DefaultLoginProvider(login),
                DefaultSessionDetails.class);
        return response.getBody();

    }

    @Override
    public Optional<SessionDetails> getSession(String login) {
        String url = UriComponentsBuilder.fromHttpUrl(findUrl).queryParam(LOGIN, login).toUriString();
        try {
            DefaultSessionDetails sessionDetails = restTemplate.getForObject(url, DefaultSessionDetails.class);
            return Optional.ofNullable(sessionDetails);
        } catch (SessionServiceException exception) {
            return Optional.empty();
        }

    }

    private String getUrl(String path) {
        String baseUrl = serviceProperties.getUrl();
        StringBuilder builder = new StringBuilder(baseUrl);

        if (baseUrl.endsWith(SUFFIX) && path.endsWith(SUFFIX)) {
            builder.deleteCharAt(builder.length() - 1);
        }
        builder.append(path);
        return builder.toString();
    }
}
