package com.bsf.account.service.service;

import com.bsf.account.service.common.model.AddAccountRequest;
import com.bsf.account.service.common.model.AddAccountResponse;

public interface AccountService {

    AddAccountResponse addAccount(AddAccountRequest addAccountRequest);

}
