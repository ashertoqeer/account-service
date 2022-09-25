package com.bsf.account.service.validator.impl;

import com.bsf.account.service.common.model.TransferFundsRequest;
import com.bsf.account.service.entities.AccountEntity;
import com.bsf.account.service.exception.FundsTransferFailedException;
import com.bsf.account.service.validator.TransactionValidator;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Objects;

@Component
public class TransactionValidatorImpl implements TransactionValidator {

    @Override
    public void validateFundsTransferRequest(AccountEntity senderAccount, AccountEntity receiverAccount,
                                             TransferFundsRequest transferFundsRequest) {
        if (isSameAccountTransfer(senderAccount, receiverAccount)) {
            throwFundsTransferFailedException("transfer to same account is not allowed");
        }

        if (doesSenderHasInSufficientBalance(senderAccount, transferFundsRequest)) {
            throwFundsTransferFailedException("sender doesn't have sufficient balance");
        }
    }

    private boolean isSameAccountTransfer(AccountEntity senderAccount, AccountEntity receiverAccount) {
        return Objects.equals(senderAccount.getId(), receiverAccount.getId());
    }

    private boolean doesSenderHasInSufficientBalance(AccountEntity senderAccount, TransferFundsRequest transferFundsRequest) {
        return senderAccount.getBalance().subtract(transferFundsRequest.getAmount()).compareTo(BigDecimal.ZERO) < 0;
    }

    private void throwFundsTransferFailedException(String reason) {
        throw new FundsTransferFailedException(reason);
    }
}
