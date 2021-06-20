package com.andreituta.onion.model.exception.factory;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.andreituta.onion.model.exception.ErrorResponse;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class ErrorResponseFactory {

    public ErrorResponse buildError(Exception e) {
        return buildError(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ErrorResponse buildError(Exception e, HttpStatus status) {
        log.debug("Error: {}", e.getStackTrace(), e);
        return ErrorResponse.builder().httpStatus(status.value()).errorMessage(e.getMessage()).build();
    }

    public ErrorResponse throwNewError(final HttpStatus status, final BindingResult br) {
        ErrorResponse error = ErrorResponse.builder()
                                           .errorMessage(br.getFieldError().getDefaultMessage())
                                           .field(getFieldPath(br.getFieldError().getArguments()))
                                           .httpStatus(status.value())
                                           .build();
        return error;
    }

    private String getFieldPath(Object[] fieldArguments) {
        DefaultMessageSourceResolvable source = (DefaultMessageSourceResolvable) fieldArguments[0];
        return source.getCodes()[0];
    }

}