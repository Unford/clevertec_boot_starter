package ru.clevertec.course.spring.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum CustomErrorCode {
    NOT_FOUND(40401, HttpStatus.NOT_FOUND),
    HANDLER_NOT_FOUND(40402, HttpStatus.NOT_FOUND),
    METHOD_NOT_SUPPORTED(40505, HttpStatus.METHOD_NOT_ALLOWED),
    MESSAGE_NOT_READABLE(40002, HttpStatus.BAD_REQUEST),
    CONSTRAINT_VIOLATION(40000, HttpStatus.BAD_REQUEST),
    TYPE_MISMATCH(40001, HttpStatus.BAD_REQUEST),
    INTERNAL_EXCEPTION(50000, HttpStatus.INTERNAL_SERVER_ERROR),
    CONFLICT(40900, HttpStatus.CONFLICT),
    BUSINESS_LOGIC(40901, HttpStatus.CONFLICT);

    private final int code;
    private final HttpStatus httpStatus;

    CustomErrorCode(int code, HttpStatus httpStatus) {
        this.code = code;
        this.httpStatus = httpStatus;
    }
}
