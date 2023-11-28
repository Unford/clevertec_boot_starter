package ru.clevertec.course.session.starter.configuration;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Role;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.clevertec.course.session.api.service.SessionService;
import ru.clevertec.course.session.impl.cleaner.SessionCleaner;
import ru.clevertec.course.session.impl.configuration.SessionDataSourceProperties;
import ru.clevertec.course.session.impl.model.UserSession;
import ru.clevertec.course.session.impl.repository.UserSessionRepository;
import ru.clevertec.course.session.impl.service.H2SessionService;
import ru.clevertec.course.session.starter.bpp.SessionHandlerBeanPostProcessor;
import ru.clevertec.course.session.starter.property.SessionBlackListProperties;
import ru.clevertec.course.session.starter.service.PropertyBlackListProvider;

import javax.sql.DataSource;
import java.beans.BeanProperty;
import java.util.Objects;

@AutoConfiguration
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@EnableConfigurationProperties(SessionBlackListProperties.class)
@AutoConfigureAfter(JpaRepositoriesAutoConfiguration.class)
@EnableJpaRepositories(basePackageClasses = {UserSession.class, UserSessionRepository.class},
        entityManagerFactoryRef = "mySessionEntityManagerFactory",
        transactionManagerRef = "mySessionTransactionManager")
@EntityScan(basePackages = "ru.clevertec.course.session.impl")
@EnableTransactionManagement
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
    @ConditionalOnMissingBean(SessionService.class)
    @ConditionalOnClass(SessionService.class)
    public H2SessionService h2SessionService(UserSessionRepository userSessionRepository) {
        return new H2SessionService(userSessionRepository);
    }


    @Bean("mySessionDataSourceProperties")
    @ConditionalOnBean(H2SessionService.class)
    @ConditionalOnClass(SessionService.class)
    public SessionDataSourceProperties sessionDataSourceProperties() {
        return new SessionDataSourceProperties();
    }

    @Bean("mySessionDataSourceProperties")
    @ConditionalOnBean(SessionDataSourceProperties.class)
    public DataSource mySessionDataSource(SessionDataSourceProperties sessionDataSourceProperties) {
        return sessionDataSourceProperties
                .initializeDataSourceBuilder()
                .build();
    }


    @Bean
    @ConditionalOnBean(name = "mySessionDataSource")
    public LocalContainerEntityManagerFactoryBean mySessionEntityManagerFactory(
            @Qualifier("mySessionDataSource") DataSource dataSource,
            EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(dataSource)
                .packages(UserSession.class)
                .build();
    }

    @Bean
    @ConditionalOnBean(name = "sessionEntityManagerFactory")
    public PlatformTransactionManager mySessionTransactionManager(
            @Qualifier("mySessionEntityManagerFactory") LocalContainerEntityManagerFactoryBean todosEntityManagerFactory) {
        return new JpaTransactionManager(Objects.requireNonNull(todosEntityManagerFactory.getObject()));
    }



    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(value = "clevertec.session.cleaner.enable", havingValue = "true")
    public SessionCleaner sessionCleaner(SessionService sessionService) {
        return new SessionCleaner(sessionService);
    }


}
