package com.test.usersapi.config.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }

        ApiError apiError =
                new ApiError(errors.isEmpty() ? ex.getLocalizedMessage() : errors.stream().collect(Collectors.joining()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler({CustomException.class})
    private ResponseEntity<Object> handlerCustomException(
            CustomException ex, WebRequest request
    ){

        ApiError apiError =
                new ApiError( ex.getMessageError());

        return ResponseEntity.status(ex.getStatus()).body(apiError);
    }

    @ExceptionHandler({Exception.class})
    private ResponseEntity<Object> handlerCustomExceptionGlobal(
            Exception ex, WebRequest request
    ){

        ApiError apiError =
                new ApiError( ex.getLocalizedMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiError);
    }
}
