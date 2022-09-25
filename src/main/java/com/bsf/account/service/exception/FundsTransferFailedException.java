package com.bsf.account.service.exception;

public class FundsTransferFailedException extends RuntimeException {

    public FundsTransferFailedException(String reason) {
        super(reason);
    }
}
