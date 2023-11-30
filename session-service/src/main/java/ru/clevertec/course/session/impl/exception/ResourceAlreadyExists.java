package ru.clevertec.course.session.impl.exception;

public class ResourceAlreadyExists extends ServiceException {

    public ResourceAlreadyExists(String message) {
        super(message);
    }

    public ResourceAlreadyExists(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceAlreadyExists(Throwable cause) {
        super(cause);
    }

    @Override
    public CustomErrorCode getErrorCode() {
        return CustomErrorCode.CONFLICT;
    }
}
