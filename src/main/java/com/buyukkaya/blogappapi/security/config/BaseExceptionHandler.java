package com.buyukkaya.blogappapi.security.config;

import com.buyukkaya.blogappapi.common.model.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class BaseExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse handlePasswordValidationException(MethodArgumentNotValidException e) {

        Map<String, String> errors = new HashMap<>();


        e.getBindingResult().getFieldErrors().forEach(error -> {
            if (errors.containsKey(error.getField())) {
                errors.put(error.getField(), String.format("%s, %s", errors.get(error.getField()), error.getDefaultMessage()));
            } else {
                errors.put(error.getField(), error.getDefaultMessage());
            }


        });

        return ApiResponse.builder()
                .response(errors)
                .status(HttpStatus.BAD_REQUEST)
                .message("Password does not match with the constraints!")
                .build();
    }

}
