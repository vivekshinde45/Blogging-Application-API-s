package com.blogapi.bloggingapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
}
