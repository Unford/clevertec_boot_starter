package ru.clevertec.course.session.starter.bpp;

import lombok.RequiredArgsConstructor;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.BeanFactory;
import ru.clevertec.course.session.starter.annotation.LoginParameter;
import ru.clevertec.course.session.starter.annotation.SessionManagement;
import ru.clevertec.course.session.starter.exception.LoginForbiddenException;
import ru.clevertec.course.session.starter.exception.SessionProxyException;
import ru.clevertec.course.session.api.model.LoginProvider;
import ru.clevertec.course.session.api.model.SessionDetails;
import ru.clevertec.course.session.starter.property.SessionBlackListProperties;
import ru.clevertec.course.session.api.service.SessionService;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class SessionProxyMethodInterceptor implements MethodInterceptor {

    private final BeanFactory beanFactory;
    private final SessionService sessionService;
    private final SessionBlackListProperties blackListProperties;


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
                throw new LoginForbiddenException("Access denied for '%s', you are on the black list".formatted(login));
            }
            Optional.of(login)
                    .flatMap(sessionService::getSession)
                    .or(() -> Optional.ofNullable(sessionService.create(login)))
                    .ifPresent(s -> this.setSessionParameters(s, parameters, arguments));

        }

        return invocation.proceed();
    }

    private Set<String> extractBlackList(SessionManagement annotation) {
        Set<String> blackList = new HashSet<>(List.of(annotation.blackList()));
        blackList.addAll(Stream.concat(annotation.includeDefaultProviders() ?
                                blackListProperties.getBlackListProviders().stream() : Stream.empty(),
                        Stream.of(annotation.blackListProviders()))
                .map(beanFactory::getBean)
                .flatMap(pr -> pr.getBlackList().stream())
                .collect(Collectors.toSet())
        );

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

    private void setSessionParameters(SessionDetails session, Parameter[] parameters, Object[] arguments) {
        Arrays.stream(parameters)
                .map(Parameter::getType)
                .filter(SessionDetails.class::isAssignableFrom)
                .findFirst()
                .orElseThrow(() -> new SessionProxyException("UserSession argument is required for Session Management"));

        for (int i = 0; i < arguments.length; i++) {
            if (SessionDetails.class.isAssignableFrom(parameters[i].getType())) {
                arguments[i] = session;
            }
        }
    }


}
