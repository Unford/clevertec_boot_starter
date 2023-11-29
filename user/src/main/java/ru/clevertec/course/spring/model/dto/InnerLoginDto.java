package ru.clevertec.course.spring.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class InnerLoginDto {
    private InnerLoginHolder holder;
    private String name;
    private String email;
    @Data
    @Accessors(chain = true)
    public static class InnerLoginHolder{
        private String login;
    }
}
