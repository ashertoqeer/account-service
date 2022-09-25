package com.bsf.account.service.common.model;

import com.bsf.account.service.common.TransactionType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class TransferFundsResponse {

    private Long id;
    private LocalDateTime createdTimestamp;
    private LocalDateTime updatedTimestamp;
    private TransactionType transactionType;
    private String reference;
    private BigDecimal amount;
    private BigDecimal startingBalance;
    private BigDecimal endingBalance;

}
