package ru.clevertec.course.session.starter.configuration;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Role;
import ru.clevertec.course.session.starter.bpp.SessionHandlerBeanPostProcessor;
import ru.clevertec.course.session.starter.property.SessionBlackListProperties;
import ru.clevertec.course.session.starter.service.PropertyBlackListProvider;

@AutoConfiguration
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@EnableConfigurationProperties(SessionBlackListProperties.class)
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

}
