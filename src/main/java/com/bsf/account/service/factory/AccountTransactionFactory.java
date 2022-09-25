package com.bsf.account.service.factory;

import com.bsf.account.service.common.TransactionType;
import com.bsf.account.service.entities.AccountEntity;
import com.bsf.account.service.entities.AccountTransactionEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class AccountTransactionFactory {

    public AccountTransactionEntity createDebitTransaction(AccountEntity account,
                                                           String reason,
                                                           String referenceId,
                                                           BigDecimal amount) {
        AccountTransactionEntity accountTransaction = createBaseTransaction(account, reason, referenceId, amount);
        accountTransaction.setTransactionType(TransactionType.DEBIT);
        accountTransaction.setEndingBalance(accountTransaction.getStartingBalance().subtract(amount));
        return accountTransaction;
    }

    public AccountTransactionEntity createCreditTransaction(AccountEntity account,
                                                            String reason,
                                                            String referenceId,
                                                            BigDecimal amount) {
        AccountTransactionEntity accountTransaction = createBaseTransaction(account, reason, referenceId, amount);
        accountTransaction.setTransactionType(TransactionType.CREDIT);
        accountTransaction.setEndingBalance(accountTransaction.getStartingBalance().add(amount));
        return accountTransaction;
    }

    private AccountTransactionEntity createBaseTransaction(AccountEntity account,
                                                           String reason,
                                                           String referenceId,
                                                           BigDecimal amount) {
        AccountTransactionEntity accountTransaction = new AccountTransactionEntity();
        accountTransaction.setAccount(account);
        accountTransaction.setReason(reason);
        accountTransaction.setReferenceId(referenceId);
        accountTransaction.setAmount(amount);
        accountTransaction.setStartingBalance(account.getBalance());
        return accountTransaction;
    }
}
