package ru.clevertec.course.spring;

import feign.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients
public class SpringVideoHostingApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringVideoHostingApplication.class, args);
    }

}
