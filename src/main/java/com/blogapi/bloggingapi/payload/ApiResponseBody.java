package com.blogapi.bloggingapi.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponseBody<T> {
    private String message;
    private boolean isSuccess;
    private T body;
}
