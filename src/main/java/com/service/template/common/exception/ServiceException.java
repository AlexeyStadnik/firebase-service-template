package com.service.template.common.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@Setter
@Builder
public class ServiceException extends RuntimeException {
    private final HttpStatus httpStatus;
    /**
     * Our custom error codes in camel case to use as message keys on frontend.
     */
    private final String errorCode;
    private final String message;
    private final Throwable cause;

    @Singular
    private final List<FieldError> errors;

    public ServiceException(final HttpStatus httpStatus, final String message) {
        this(httpStatus, null, message, null, null);
    }

    public ServiceException(final HttpStatus httpStatus, final String message,
                            final Throwable cause) {
        this(httpStatus, null, message, cause, null);
    }

    public ServiceException(final HttpStatus httpStatus,
                            final String errorCode,
                            final String message,
                            final Throwable cause,
                            final List<FieldError> errors) {
        super(message, cause);
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.message = message;
        this.cause = cause;
        this.errors = errors;
    }
}
