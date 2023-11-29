package ru.clevertec.course.session.starter.bpp;

import lombok.RequiredArgsConstructor;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.BeanFactory;
import ru.clevertec.course.session.api.model.LoginProvider;
import ru.clevertec.course.session.api.model.SessionDetails;
import ru.clevertec.course.session.api.service.SessionService;
import ru.clevertec.course.session.starter.annotation.LoginParameter;
import ru.clevertec.course.session.starter.annotation.SessionManagement;
import ru.clevertec.course.session.starter.exception.LoginForbiddenException;
import ru.clevertec.course.session.starter.exception.SessionProxyException;
import ru.clevertec.course.session.starter.property.SessionBlackListProperties;

import java.lang.reflect.Field;
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

            Parameter[] parameters = method.getParameters();
            Object[] arguments = invocation.getArguments();

            String login = getLogin(parameters, arguments)
                    .orElseThrow(() -> new SessionProxyException("Login parameter not found, provide LoginProvider or" +
                            " annotate @LoginParameter"));
            Optional.of(login)
                    .map(l -> this.checkBlackList(l, annotation))
                    .flatMap(sessionService::getSession)
                    .or(() -> Optional.ofNullable(sessionService.create(login)))
                    .ifPresent(s -> this.setSessionParameters(s, parameters, arguments));

        }

        return invocation.proceed();
    }

    private String checkBlackList(String login, SessionManagement annotation) {
        Set<String> blackList = extractBlackList(annotation);
        if (blackList.contains(login)) {
            throw new LoginForbiddenException("Access denied for '%s', you are on the black list".formatted(login));
        }
        return login;
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
            if (p.isAnnotationPresent(LoginParameter.class)) {
                if (String.class.isAssignableFrom(p.getType())) {
                    return Optional.of((String) arguments[i]);
                } else {
                    String pattern = p.getAnnotation(LoginParameter.class).value();
                    return extractLoginFromField(arguments[i], pattern);
                }
            }else if (LoginProvider.class.isAssignableFrom(p.getType())) {
                LoginProvider loginProvider = (LoginProvider) arguments[i];
                return Optional.ofNullable(loginProvider.getLogin());
            }
        }
        return Optional.empty();
    }

    private Optional<String> extractLoginFromField(Object argument, String pattern) {
        String[] fieldNames = pattern.split("\\.");
        Object fieldValue = argument;
        try {
            for (String fieldName : fieldNames) {
                if (fieldValue == null) return Optional.empty();
                Field field = fieldValue.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                fieldValue = field.get(fieldValue);
            }
        } catch (IllegalAccessException e) {
            throw new SessionProxyException("Can't access field " + pattern, e);
        } catch (NoSuchFieldException e) {
            throw new SessionProxyException("Field %s not found".formatted(pattern), e);
        }

        return Optional.ofNullable(fieldValue.toString());

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
