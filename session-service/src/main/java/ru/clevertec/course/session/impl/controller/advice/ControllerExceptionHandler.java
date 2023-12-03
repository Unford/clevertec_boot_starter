package ru.clevertec.course.session.impl.controller.advice;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.hibernate.validator.internal.engine.path.NodeImpl;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.clevertec.course.session.impl.dto.ExceptionResponse;
import ru.clevertec.course.session.impl.exception.CustomErrorCode;
import ru.clevertec.course.session.impl.exception.ServiceException;


import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {
    private static final String CONSTRAINT_VIOLATION_MESSAGE_PATTERN = "[%s]:'%s' %s";


    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
                                                                   HttpStatusCode status, WebRequest request) {
        CustomErrorCode errorCode = CustomErrorCode.HANDLER_NOT_FOUND;
        ExceptionResponse errorInfo = new ExceptionResponse(errorCode.getCode(), ex.getMessage());
        return new ResponseEntity<>(errorInfo, errorCode.getHttpStatus());
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
                                                                         HttpHeaders headers, HttpStatusCode status,
                                                                         WebRequest request) {
        CustomErrorCode errorCode = CustomErrorCode.METHOD_NOT_SUPPORTED;
        ExceptionResponse errorInfo = new ExceptionResponse(errorCode.getCode(), ex.getMessage());
        return new ResponseEntity<>(errorInfo, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatusCode status,
                                                                  WebRequest request) {
        CustomErrorCode errorCode = CustomErrorCode.MESSAGE_NOT_READABLE;
        ExceptionResponse errorInfo = new ExceptionResponse(errorCode.getCode(), ex.getMessage());
        return new ResponseEntity<>(errorInfo, status);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatusCode status,
                                                                  WebRequest request) {
        CustomErrorCode errorCode = CustomErrorCode.CONSTRAINT_VIOLATION;
        String errorMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fe -> fe == null ? "[null]" : fe.getField()
                        + ":[" + fe.getRejectedValue() + "]: "
                        + fe.getDefaultMessage())
                .collect(Collectors.joining("; "));
        ExceptionResponse errorInfo = new ExceptionResponse(errorCode.getCode(), errorMessage);
        return new ResponseEntity<>(errorInfo, errorCode.getHttpStatus());
    }


    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        CustomErrorCode errorCode = CustomErrorCode.TYPE_MISMATCH;

        String message = ex.getParameter().getParameterName() +
                ex.getParameter().getParameter().getType().getName();
        ExceptionResponse errorInfo = new ExceptionResponse(errorCode.getCode(), message);
        return new ResponseEntity<>(errorInfo, errorCode.getHttpStatus());
    }


    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ExceptionResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        CustomErrorCode errorCode = CustomErrorCode.CONSTRAINT_VIOLATION;
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        String message = constraintViolations.stream()
                .map(cv -> {
                    NodeImpl leafNode = ((PathImpl) cv.getPropertyPath()).getLeafNode();
                    String fieldName = leafNode.getParent().getValue() instanceof Collection ?
                            leafNode.getParent().getName() : leafNode.getName();
                    return String.format(CONSTRAINT_VIOLATION_MESSAGE_PATTERN,
                            fieldName, cv.getInvalidValue(), cv.getMessage());
                })
                .collect(Collectors.joining("; "));
        ExceptionResponse errorInfo = new ExceptionResponse(errorCode.getCode(), message);
        return new ResponseEntity<>(errorInfo, errorCode.getHttpStatus());
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ExceptionResponse> handleServiceException(ServiceException ex) {
        CustomErrorCode errorCode = ex.getErrorCode();
        ExceptionResponse errorInfo = new ExceptionResponse(errorCode.getCode(), ex.getMessage());
        return new ResponseEntity<>(errorInfo, errorCode.getHttpStatus());
    }


    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ExceptionResponse> handleExceptionInternal(Exception ex) {
        CustomErrorCode errorCode = CustomErrorCode.INTERNAL_EXCEPTION;
        ExceptionResponse errorInfo = new ExceptionResponse(errorCode.getCode(), ex.getMessage());
        return new ResponseEntity<>(errorInfo, errorCode.getHttpStatus());
    }
}
