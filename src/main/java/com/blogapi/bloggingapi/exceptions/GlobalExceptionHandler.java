package com.blogapi.bloggingapi.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.blogapi.bloggingapi.payload.ApiResponseBody;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponseBody> resourseNotFoundExceptionHandler(ResourceNotFoundException ex) {
        ApiResponseBody responseBody = new ApiResponseBody(ex.getMessage(), false);
        return new ResponseEntity<ApiResponseBody>(responseBody, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> methodArgumentNotValidExceptionHandler(
            MethodArgumentNotValidException ex) {
        Map<String, String> responseBody = new HashMap<>();

        // get the errors for required field
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            responseBody.put(fieldName, errorMessage);
        });

        return new ResponseEntity<Map<String, String>>(responseBody, HttpStatus.BAD_REQUEST);
    }
}
