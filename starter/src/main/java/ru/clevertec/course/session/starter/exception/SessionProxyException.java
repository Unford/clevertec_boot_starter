package ru.clevertec.course.session.starter.exception;

public class SessionProxyException extends RuntimeException {
    public SessionProxyException() {
        super();
    }

    public SessionProxyException(String message) {
        super(message);
    }

    public SessionProxyException(String message, Throwable cause) {
        super(message, cause);
    }

    public SessionProxyException(Throwable cause) {
        super(cause);
    }
}
