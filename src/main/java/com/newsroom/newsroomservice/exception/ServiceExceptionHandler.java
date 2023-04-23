package com.newsroom.newsroomservice.exception;


import com.service.model.rest.RestError;
import com.service.model.rest.RestFieldError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class ServiceExceptionHandler extends ResponseEntityExceptionHandler {

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
        final HttpStatus httpStatus
                = ex.getHttpStatus() == null ? HttpStatus.INTERNAL_SERVER_ERROR : ex.getHttpStatus();
        return handleExceptionInternal(ex, restError, headers, httpStatus, request);
    }
}
