package com.bsf.account.service.mapper;

import com.bsf.account.service.common.model.TransferFundsResponse;
import com.bsf.account.service.entities.AccountTransactionEntity;

public class TransactionMapper {

    private TransactionMapper() {

    }
    public static TransferFundsResponse transactionToTransferFundsResponseMapper(AccountTransactionEntity transaction) {
        TransferFundsResponse transferFundsResponse = new TransferFundsResponse();
        transferFundsResponse.setId(transaction.getId());
        transferFundsResponse.setCreatedTimestamp(transaction.getCreatedTimestamp());
        transferFundsResponse.setUpdatedTimestamp(transaction.getUpdatedTimestamp());
        transferFundsResponse.setAmount(transaction.getAmount());
        transferFundsResponse.setTransactionType(transaction.getTransactionType());
        transferFundsResponse.setReference(transaction.getReferenceId());
        transferFundsResponse.setStartingBalance(transaction.getStartingBalance());
        transferFundsResponse.setEndingBalance(transaction.getEndingBalance());
        return transferFundsResponse;
    }
}
