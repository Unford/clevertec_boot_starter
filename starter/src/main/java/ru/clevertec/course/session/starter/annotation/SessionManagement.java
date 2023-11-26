package ru.clevertec.course.session.starter.annotation;

import ru.clevertec.course.session.starter.service.BlackListProvider;
import ru.clevertec.course.session.starter.service.PropertyBlackListProvider;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SessionManagement {
    Class<? extends BlackListProvider>[] blackListProviders() default {PropertyBlackListProvider.class};
    String[] blackList() default {};
    boolean includeProviders() default true;
}
