package com.bsf.account.service.service;

import com.bsf.account.service.common.model.TransferFundsRequest;
import com.bsf.account.service.common.model.TransferFundsResponse;

public interface FundsService {

    TransferFundsResponse transferFunds(Long senderAccountId, TransferFundsRequest transferFundsRequest);

}
