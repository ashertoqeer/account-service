package com.bsf.account.service.common.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

@Getter
@NoArgsConstructor
public class ApiError {

    private HttpStatus status;
    private String message;
    private List<String> errors;
    public ApiError(HttpStatus status, String message, List<String> errors) {
        super();
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public ApiError(HttpStatus status, String message, String error) {
        this(status, message, Collections.singletonList(error));
    }
}
