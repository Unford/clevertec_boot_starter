package ru.clevertec.course.session.starter.bpp;

import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanPostProcessor;
import ru.clevertec.course.session.starter.annotation.SessionManagement;
import ru.clevertec.course.session.starter.service.SessionService;

import java.lang.reflect.Constructor;
import java.util.*;


public class SessionHandlerBeanPostProcessor implements BeanPostProcessor, BeanFactoryAware {
    private final Map<String, Class<?>> beanNamesWithAnnotatedMethods = new HashMap<>();
    private BeanFactory beanFactory;
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> clazz = bean.getClass();
        boolean annotationPresent = Arrays.stream(clazz.getMethods())
                .anyMatch(method -> method.isAnnotationPresent(SessionManagement.class));
        if (annotationPresent) {
            beanNamesWithAnnotatedMethods.put(beanName, clazz);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return Optional.ofNullable(beanNamesWithAnnotatedMethods.get(beanName))
                .map(clazz -> getSessionProxy(bean))
                .orElse(bean);
    }

    private Object getSessionProxy(Object bean) {
        SessionService sessionService = beanFactory.getBean(SessionService.class);
        ProxyFactory factory = new ProxyFactory(bean);
        factory.addAdvice(new SessionProxyMethodInterceptor(beanFactory, sessionService));
        return factory.getProxy();
    }


}
