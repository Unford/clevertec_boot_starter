package ru.clevertec.course.session.starter.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Role;
import ru.clevertec.course.session.api.service.SessionService;
import ru.clevertec.course.session.starter.bpp.SessionHandlerBeanPostProcessor;
import ru.clevertec.course.session.starter.cleaner.SessionCleaner;
import ru.clevertec.course.session.starter.property.SessionBlackListProperties;
import ru.clevertec.course.session.starter.service.MapSessionService;
import ru.clevertec.course.session.starter.service.PropertyBlackListProvider;

@AutoConfiguration
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@EnableConfigurationProperties(SessionBlackListProperties.class)
@AutoConfigureAfter(JpaRepositoriesAutoConfiguration.class)
@ConditionalOnProperty(value = "clevertec.session.enable", havingValue = "true")
@Slf4j
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
    @ConditionalOnMissingBean(SessionService.class)
    public MapSessionService mapSessionService() {
        log.warn("default hash map session service create");
        return new MapSessionService();
    }

    @Bean
    @ConditionalOnBean(SessionService.class)
    @ConditionalOnProperty(value = "clevertec.session.cleaner.enable", havingValue = "true")
    public SessionCleaner sessionCleaner(SessionService sessionService) {
        log.info("{} is created", SessionCleaner.class.getName());
        return new SessionCleaner(sessionService);
    }


}
