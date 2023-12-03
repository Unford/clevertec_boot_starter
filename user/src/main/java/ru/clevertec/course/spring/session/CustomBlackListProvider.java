package ru.clevertec.course.spring.session;

import org.springframework.stereotype.Component;
import ru.clevertec.course.session.api.service.provider.BlackListProvider;

import java.util.Set;

@Component
public class CustomBlackListProvider implements BlackListProvider {
    @Override
    public Set<String> getBlackList() {
        return Set.of("user1", "ANTON");
    }
}
