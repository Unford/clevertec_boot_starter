package ru.clevertec.course.session.starter.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "clevertec.session.service")
public class SessionServiceProperties {
    private String url = "";
    private String findPath = "";
    private String createPath = "";

}
