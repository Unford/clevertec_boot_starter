package ru.clevertec.course.session.starter.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import ru.clevertec.course.session.starter.service.BlackListProvider;

import java.util.HashSet;
import java.util.Set;

@Data
@ConfigurationProperties(prefix = "clevertec.session.default")
public class SessionBlackListProperties {
    private final Set<String> blackList = new HashSet<>();
    private final Set<Class<? extends BlackListProvider>> blackListProviders = new HashSet<>();
}
