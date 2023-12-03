package ru.clevertec.course.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SpringVideoHostingApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringVideoHostingApplication.class, args);
    }

}
