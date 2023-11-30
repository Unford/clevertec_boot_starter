package ru.clevertec.course.spring.session;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.clevertec.course.session.api.service.provider.BlackListProvider;
import ru.clevertec.course.spring.gateway.BlackListGateway;

import java.util.HashSet;
import java.util.Set;


@Component
@RequiredArgsConstructor
public class GatewayBlackListProvider implements BlackListProvider {
    private final BlackListGateway blackListGateway;

    @Override
    public Set<String> getBlackList() {
        return new HashSet<>(blackListGateway.getBlackList().logins());
    }
}
