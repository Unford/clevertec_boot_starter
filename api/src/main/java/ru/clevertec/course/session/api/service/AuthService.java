package ru.clevertec.course.session.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.course.session.api.model.dto.response.SessionResponse;
import ru.clevertec.course.session.api.model.mapper.SessionMapper;
import ru.clevertec.course.session.starter.annotation.LoginParameter;
import ru.clevertec.course.session.starter.annotation.SessionManagement;
import ru.clevertec.course.session.starter.model.LoginProvider;
import ru.clevertec.course.session.starter.model.SessionDetails;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final SessionMapper sessionMapper;

    @SessionManagement
    public SessionResponse getSession(@LoginParameter String login, SessionDetails sessionDetails){
        return sessionMapper.toResponse(sessionDetails);
    }

    @SessionManagement
    @Transactional
    public SessionResponse getSession(LoginProvider login, SessionDetails sessionDetails){
        return sessionMapper.toResponse(sessionDetails);
    }


}
