package ru.clevertec.course.session.starter.configuration;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Role;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import ru.clevertec.course.session.api.service.SessionService;
import ru.clevertec.course.session.starter.bpp.SessionHandlerBeanPostProcessor;
import ru.clevertec.course.session.starter.exception.SessionServiceException;
import ru.clevertec.course.session.starter.property.SessionBlackListProperties;
import ru.clevertec.course.session.starter.property.SessionServiceProperties;
import ru.clevertec.course.session.starter.service.DefaultSessionService;
import ru.clevertec.course.session.starter.service.PropertyBlackListProvider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@AutoConfiguration
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@EnableConfigurationProperties({SessionBlackListProperties.class, SessionServiceProperties.class})
@ConditionalOnProperty(value = "clevertec.session.enable", havingValue = "true")
public class SessionHandlerAutoConfiguration {
    @Bean
    public SessionHandlerBeanPostProcessor sessionHandlerBeanPostProcessor() {
        return new SessionHandlerBeanPostProcessor();
    }

    @Bean
    public PropertyBlackListProvider propertyBlackListProvider(SessionBlackListProperties sessionBlackListProperties) {
        return new PropertyBlackListProvider(sessionBlackListProperties);
    }

    @Bean
    @ConditionalOnBean(value = SessionService.class)
    public DefaultSessionService defaultSessionService(RestTemplate restTemplate,
                                                       SessionServiceProperties serviceProperties) {
        return new DefaultSessionService(restTemplate, serviceProperties);
    }

    @Bean
    @ConditionalOnBean(value = DefaultSessionService.class)
    @ConditionalOnMissingBean(value = RestTemplate.class)
    public RestTemplate sessionServiceRestTemplate(RestTemplateBuilder templateBuilder) {
        return templateBuilder
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .errorHandler(new DefaultResponseErrorHandler())
                .build();
    }

    private static class DefaultResponseErrorHandler implements ResponseErrorHandler {

        @Override
        public boolean hasError(ClientHttpResponse response) throws IOException {
            return response.getStatusCode().isError();
        }

        @Override
        public void handleError(ClientHttpResponse response) throws IOException {
            String errorMessage = new BufferedReader(new InputStreamReader(response.getBody()))
                    .lines()
                    .collect(Collectors.joining());
            throw new SessionServiceException(errorMessage);
        }
    }


}
