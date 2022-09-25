package com.bsf.account.service.validator;

import com.bsf.account.service.common.model.TransferFundsRequest;
import com.bsf.account.service.entities.AccountEntity;

public interface TransactionValidator {

    void validateFundsTransferRequest(AccountEntity senderAccount,
                                     AccountEntity receiverAccount,
                                     TransferFundsRequest transferFundsRequest);

}
