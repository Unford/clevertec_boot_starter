package ru.clevertec.course.session.starter.exception;

public class SessionServiceException extends RuntimeException {
    public SessionServiceException() {
        super();
    }

    public SessionServiceException(String message) {
        super(message);
    }

    public SessionServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public SessionServiceException(Throwable cause) {
        super(cause);
    }
}
