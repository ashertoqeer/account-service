package com.bsf.account.service.service.impl;

import com.bsf.account.service.entities.AccountEntity;
import com.bsf.account.service.exception.AccountAlreadyExistsException;
import com.bsf.account.service.repositories.AccountRepository;
import com.bsf.account.service.service.AccountService;
import com.bsf.account.service.common.model.AddAccountRequest;
import com.bsf.account.service.common.model.AddAccountResponse;
import com.bsf.account.service.mapper.AccountMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public AddAccountResponse addAccount(AddAccountRequest addAccountRequest) {
        validateAccountNotExists(addAccountRequest.getEmail());
        AccountEntity account = AccountMapper.addAccountRequestToAccountEntity(addAccountRequest);
        accountRepository.save(account);
        return AccountMapper.accountEntityToAddAccountResponse(account);
    }

    private void validateAccountNotExists(String email) {
        if (accountRepository.existsByEmail(email)) {
            throw new AccountAlreadyExistsException(email);
        }
    }
}
