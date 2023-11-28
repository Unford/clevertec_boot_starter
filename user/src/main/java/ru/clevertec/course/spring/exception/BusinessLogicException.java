package ru.clevertec.course.spring.exception;

public class BusinessLogicException extends ServiceException{
    public BusinessLogicException(String message) {
        super(message);
    }

    public BusinessLogicException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessLogicException(Throwable cause) {
        super(cause);
    }

    @Override
    public CustomErrorCode getErrorCode() {
        return CustomErrorCode.BUSINESS_LOGIC;
    }
}
