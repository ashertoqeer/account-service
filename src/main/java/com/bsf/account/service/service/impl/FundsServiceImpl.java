package com.bsf.account.service.service.impl;

import com.bsf.account.service.common.model.TransferFundsRequest;
import com.bsf.account.service.common.model.TransferFundsResponse;
import com.bsf.account.service.entities.AccountEntity;
import com.bsf.account.service.entities.AccountTransactionEntity;
import com.bsf.account.service.exception.AccountNotFoundException;
import com.bsf.account.service.factory.AccountTransactionFactory;
import com.bsf.account.service.mapper.TransactionMapper;
import com.bsf.account.service.repositories.AccountRepository;
import com.bsf.account.service.repositories.AccountTransactionRepository;
import com.bsf.account.service.service.FundsService;
import com.bsf.account.service.validator.TransactionValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.UUID;

@Service
@AllArgsConstructor
public class FundsServiceImpl implements FundsService {

    private AccountRepository accountRepository;
    private AccountTransactionRepository accountTransactionRepository;
    private AccountTransactionFactory accountTransactionFactory;
    private TransactionValidator transactionValidator;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public TransferFundsResponse transferFunds(Long senderAccountId, TransferFundsRequest transferFundsRequest) {
        AccountEntity senderAccount = accountRepository.findById(senderAccountId)
                .orElseThrow(() -> accountNotFoundException(senderAccountId));
        AccountEntity receiverAccount = accountRepository.findById(transferFundsRequest.getReceiverAccount())
                .orElseThrow(() -> accountNotFoundException(transferFundsRequest.getReceiverAccount()));

        transactionValidator.validateFundsTransferRequest(senderAccount, receiverAccount, transferFundsRequest);

        BigDecimal transactionAmount = transferFundsRequest.getAmount();

        TransferFundsResponse transferFundsResponse = addTransactions(senderAccount, receiverAccount,
                transferFundsRequest.getReason(), transactionAmount);

        updateBalance(senderAccount, receiverAccount, transactionAmount);

        return transferFundsResponse;
    }

    private TransferFundsResponse addTransactions(AccountEntity senderAccount,
                                                  AccountEntity receiverAccount,
                                                  String reason,
                                                  BigDecimal transactionAmount) {
        String transactionReference = UUID.randomUUID().toString();

        AccountTransactionEntity debitTransaction = accountTransactionFactory
                .createDebitTransaction(
                        senderAccount,
                        reason,
                        transactionReference,
                        transactionAmount);

        AccountTransactionEntity creditTransaction = accountTransactionFactory
                .createCreditTransaction(
                        receiverAccount,
                        reason,
                        transactionReference,
                        transactionAmount);

        accountTransactionRepository.saveAll(Arrays.asList(debitTransaction, creditTransaction));
        return TransactionMapper.transactionToTransferFundsResponseMapper(debitTransaction);
    }

    private static void updateBalance(AccountEntity senderAccount, AccountEntity receiverAccount, BigDecimal transactionAmount) {
        senderAccount.setBalance(senderAccount.getBalance().subtract(transactionAmount));
        receiverAccount.setBalance(receiverAccount.getBalance().add(transactionAmount));
    }

    private AccountNotFoundException accountNotFoundException(Long id) {
        return new AccountNotFoundException(id);
    }
}
