package ru.clevertec.course.session.starter.exception;

public class LoginForbiddenException extends RuntimeException {

    public LoginForbiddenException() {
        super();
    }

    public LoginForbiddenException(String message) {
        super(message);
    }

    public LoginForbiddenException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginForbiddenException(Throwable cause) {
        super(cause);
    }
}
