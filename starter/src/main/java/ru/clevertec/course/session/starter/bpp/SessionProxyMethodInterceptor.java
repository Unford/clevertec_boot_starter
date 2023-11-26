package ru.clevertec.course.session.starter.bpp;

import lombok.RequiredArgsConstructor;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.beans.factory.BeanFactory;
import ru.clevertec.course.session.starter.annotation.LoginParameter;
import ru.clevertec.course.session.starter.annotation.SessionManagement;
import ru.clevertec.course.session.starter.exception.LoginForbiddenException;
import ru.clevertec.course.session.starter.exception.SessionProxyException;
import ru.clevertec.course.session.starter.model.LoginProvider;
import ru.clevertec.course.session.starter.model.UserSession;
import ru.clevertec.course.session.starter.service.SessionService;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class SessionProxyMethodInterceptor implements MethodInterceptor {

    private final BeanFactory beanFactory;
    private final SessionService sessionService;


    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Method method = invocation.getMethod();
        SessionManagement annotation = method.getAnnotation(SessionManagement.class);
        if (Objects.nonNull(annotation)) {
            Set<String> blackList = extractBlackList(annotation);
            Parameter[] parameters = method.getParameters();
            Object[] arguments = invocation.getArguments();

            String login = getLogin(parameters, arguments)
                    .orElseThrow(() -> new SessionProxyException("Login parameter not found, provide LoginProvider or" +
                            " String annotated @LoginParameter"));
            if (blackList.contains(login)) {
                throw new LoginForbiddenException("login access denied, you are on the black list");
            }
            Optional.of(login)
                    .flatMap(sessionService::getSession)
                    .or(() -> Optional.ofNullable(sessionService.create(login)))
                    .map(s -> this.setSessionParameters(s, arguments));

        }

        return invocation.proceed();
    }

    private Set<String> extractBlackList(SessionManagement annotation) {
        Set<String> blackList = new HashSet<>(List.of(annotation.blackList()));

        if (annotation.includeProviders()) {
            blackList.addAll(Arrays.stream(annotation.blackListProviders())
                    .map(beanFactory::getBean)
                    .flatMap(pr -> pr.getBlackList().stream())
                    .collect(Collectors.toSet()));
        }
        return blackList;
    }

    private Optional<String> getLogin(Parameter[] parameters, Object[] arguments) {
        for (int i = 0; i < arguments.length; i++) {
            Parameter p = parameters[i];
            if (String.class.isAssignableFrom(p.getType()) && p.isAnnotationPresent(LoginParameter.class)) {
                return Optional.of((String) arguments[i]);
            }
            if (LoginProvider.class.isAssignableFrom(p.getType())) {
                LoginProvider loginProvider = (LoginProvider) arguments[i];
                return Optional.ofNullable(loginProvider.getLogin());
            }
        }
        return Optional.empty();
    }

    private UserSession setSessionParameters(UserSession session, Object[] arguments) {
        boolean check = false;
        for (int i = 0; i < arguments.length; i++) {
            if (arguments[i] instanceof UserSession) {
                arguments[i] = session;
                check = true;
            }
        }
        if (!check) throw new SessionProxyException("UserSession argument is required for Session Management");
        return session;
    }


}
