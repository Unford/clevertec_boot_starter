package ru.clevertec.course.session.starter.annotation;

import ru.clevertec.course.session.api.service.provider.BlackListProvider;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SessionManagement {
    Class<? extends BlackListProvider>[] blackListProviders() default {};
    String[] blackList() default {};
    boolean includeDefaultProviders() default true;
}
