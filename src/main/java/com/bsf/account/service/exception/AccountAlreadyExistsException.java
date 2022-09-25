package com.bsf.account.service.exception;

import lombok.Getter;

@Getter
public class AccountAlreadyExistsException extends RuntimeException {

    public AccountAlreadyExistsException(String email) {
        super("Account already exists against email: " + email);
    }
}
