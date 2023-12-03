package ru.clevertec.course.spring.exception;

public class ImageUploadException extends ServiceException {
    public ImageUploadException(String message) {
        super(message);
    }

    public ImageUploadException(String message, Throwable cause) {
        super(message, cause);
    }

    public ImageUploadException(Throwable cause) {
        super(cause);
    }

    @Override
    public CustomErrorCode getErrorCode() {
        return CustomErrorCode.INTERNAL_EXCEPTION;
    }
}
