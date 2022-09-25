package com.bsf.account.service.entities;

import com.bsf.account.service.common.TransactionType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "account_transaction")
@Getter
@Setter
public class AccountTransactionEntity extends BaseEntity {

    @Column(name = "reason")
    private String reason;

    @Column(name = "reference_id")
    private String referenceId;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "starting_balance")
    private BigDecimal startingBalance;

    @Column(name = "ending_balance")
    private BigDecimal endingBalance;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private AccountEntity account;

}
