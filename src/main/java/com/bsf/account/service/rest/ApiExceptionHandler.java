package com.bsf.account.service.rest;

import com.bsf.account.service.exception.AccountAlreadyExistsException;
import com.bsf.account.service.common.model.ApiError;
import com.bsf.account.service.exception.AccountNotFoundException;
import com.bsf.account.service.exception.FundsTransferFailedException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        List<String> apiErrors = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        ApiError apiError = new ApiError(status, status.getReasonPhrase(), apiErrors);

        return super.handleExceptionInternal(ex, apiError, headers, status, request);
    }

    @ExceptionHandler({AccountAlreadyExistsException.class, AccountNotFoundException.class, FundsTransferFailedException.class})
    protected ResponseEntity<Object> handleApiException(RuntimeException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ApiError apiError = new ApiError(status, status.getReasonPhrase(), ex.getMessage());
        return super.handleExceptionInternal(ex, apiError, new HttpHeaders(), status, request);
    }
}
