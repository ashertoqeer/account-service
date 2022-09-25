package com.bsf.account.service.exception;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(Long id) {
        super("account not found: " + id);
    }
}
