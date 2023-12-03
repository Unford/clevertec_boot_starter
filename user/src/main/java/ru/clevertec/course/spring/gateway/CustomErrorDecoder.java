package ru.clevertec.course.spring.gateway;

import feign.Response;
import feign.codec.ErrorDecoder;
import ru.clevertec.course.spring.exception.ResourceNotFoundException;

public class CustomErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() == 404) {
            return new ResourceNotFoundException("Session not found");
        }
        return new Exception("Exception while getting session");
    }
}
