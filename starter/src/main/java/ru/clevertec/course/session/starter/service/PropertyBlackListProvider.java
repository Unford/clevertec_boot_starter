package ru.clevertec.course.session.starter.service;

import lombok.RequiredArgsConstructor;
import ru.clevertec.course.session.starter.property.SessionBlackListProperties;

import java.util.Set;

@RequiredArgsConstructor
public class PropertyBlackListProvider implements BlackListProvider {
    private final SessionBlackListProperties blackListProperties;

    @Override
    public Set<String> getBlackList() {
        return blackListProperties.getBlackList();
    }
}
