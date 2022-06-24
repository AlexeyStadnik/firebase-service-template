package com.service.template.common.exception;


import com.service.model.rest.RestError;
import com.service.model.rest.RestFieldError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NestedRuntimeException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.lang.String.format;

@ControllerAdvice
@Slf4j
public class ServiceExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Pattern removeJavaClassPrefixPattern = Pattern
            .compile("\\w+\\.\\w+");

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<Object> handleServiceException(
            final ServiceException ex, final WebRequest request) {
        final RestError restError = new RestError();
        restError.setMessage(ex.getMessage());
        restError.setErrorCode(ex.getErrorCode());

        if (ex.getErrors() != null) {
            final List<RestFieldError> errors = ex.getErrors().stream()
                    .map(err -> {
                                final RestFieldError restFieldError = new RestFieldError();
                                restFieldError.setField(err.getFieldName());
                                restFieldError.setMessage(err.getMessage());
                                restFieldError.setErrorCode(err.getErrorCode());
                                return restFieldError;
                            }
                    ).collect(Collectors.toList());

            restError.setErrors(errors);
        }

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        final HttpStatus httpStatus = ex.getHttpStatus() == null
                ? HttpStatus.INTERNAL_SERVER_ERROR
                : ex.getHttpStatus();
        return handleExceptionInternal(ex, restError, headers, httpStatus, request);
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(
            final MethodArgumentNotValidException ex, final HttpHeaders headers,
            final HttpStatus status, final WebRequest request) {
        final RestError restError = new RestError();
        restError.setMessage("Invalid request object");
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (ex.getBindingResult().getFieldErrorCount() > 0) {
            final List<RestFieldError> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
                    .map(err -> {
                        final RestFieldError restFieldError = new RestFieldError();
                        restFieldError.setField(
                                err.getField());
                        restFieldError.setMessage(
                                err.getDefaultMessage());
                        return restFieldError;
                    })
                    .collect(Collectors.toList());
            restError.setErrors(fieldErrors);
        }
        return handleExceptionInternal(ex, restError, headers, HttpStatus.BAD_REQUEST, request);
    }


    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(
            final ConstraintViolationException ex, final WebRequest request) {
        final RestError restError = new RestError();
        restError.setMessage("Invalid request object");
        final List<RestFieldError> errorList = ex.getConstraintViolations().stream()
                .map(violation -> {
                    final String path = violation.getPropertyPath().toString();
                    final String replaced = removeJavaClassPrefixPattern.matcher(path).replaceFirst("");
                    return new RestFieldError().message(violation.getMessage())
                            .field(replaced);
                }).collect(Collectors.toList());
        restError.setErrors(errorList);
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return handleExceptionInternal(ex, restError, headers, HttpStatus.BAD_REQUEST, request);
    }

    /**
     * Handles {@link HttpMessageConversionException}.
     *
     * <p>By default there is no detailed message available what went wrong during http message conversion.
     * This handler tries to provide specific value which brakes the conversion
     *
     * @param ex      exception
     * @param request http request
     * @return response enttiy with message and status code
     */
    @ExceptionHandler(HttpMessageConversionException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(
            final HttpMessageConversionException ex, final WebRequest request) {
        return handleIllegalExceptionInternal(ex, request);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleMethodArgumentTypeMismatchException(
            final MethodArgumentTypeMismatchException ex, final WebRequest request) {
        return handleIllegalExceptionInternal(ex, request);
    }

    private <T extends NestedRuntimeException> ResponseEntity<Object> handleIllegalExceptionInternal(
            final T ex, final WebRequest request) {
        final String specificMessage = ex.getMostSpecificCause().getMessage();

        final RestError restError = new RestError();
        restError.setMessage(specificMessage == null ? ex.getMessage() : specificMessage);

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return handleExceptionInternal(ex, restError, headers, HttpStatus.BAD_REQUEST, request);
    }


    @ExceptionHandler
    public ResponseEntity<Object> handleOtherException(
            final AccessDeniedException ex, final WebRequest request) {
        final RestError restError = new RestError();
        restError.setMessage(ex.getMessage());
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return handleExceptionInternal(ex, restError, headers, HttpStatus.FORBIDDEN, request);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleOtherException(
            final Exception ex, final WebRequest request) {
        log.error("", ex);
        final RestError restError = new RestError();
        restError.setMessage("An error has occurred, please try again later");
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return handleExceptionInternal(ex, restError, headers, HttpStatus.INTERNAL_SERVER_ERROR,
                request);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            final MissingServletRequestParameterException ex,
            final HttpHeaders headers,
            final HttpStatus status,
            final WebRequest request) {
        final RestError restError = new RestError();
        restError.setErrorCode(DefaultErrorCodeConstants.INVALID_REQUEST);
        restError.setMessage(
                format("Required request '%s' parameter is not present", ex.getParameterName()));
        headers.setContentType(MediaType.APPLICATION_JSON);
        return handleExceptionInternal(ex, restError, headers, HttpStatus.BAD_REQUEST, request);
    }
}
