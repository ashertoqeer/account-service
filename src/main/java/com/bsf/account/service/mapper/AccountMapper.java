package com.bsf.account.service.mapper;

import com.bsf.account.service.common.model.AddAccountRequest;
import com.bsf.account.service.entities.AccountEntity;
import com.bsf.account.service.common.model.AddAccountResponse;

public final class AccountMapper {

    private AccountMapper() {

    }
    public static AccountEntity addAccountRequestToAccountEntity(AddAccountRequest addAccountRequest) {
        AccountEntity account = new AccountEntity();
        account.setTitle(addAccountRequest.getTitle());
        account.setEmail(addAccountRequest.getEmail());
        account.setBalance(addAccountRequest.getBalance());
        return account;
    }

    public static AddAccountResponse accountEntityToAddAccountResponse(AccountEntity accountEntity) {
        AddAccountResponse addAccountResponse = new AddAccountResponse();
        addAccountResponse.setId(accountEntity.getId());
        addAccountResponse.setEmail(accountEntity.getEmail());
        addAccountResponse.setTitle(accountEntity.getTitle());
        addAccountResponse.setBalance(accountEntity.getBalance());
        return addAccountResponse;
    }
}
