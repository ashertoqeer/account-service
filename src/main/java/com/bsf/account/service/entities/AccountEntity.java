package com.bsf.account.service.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "account")
@Getter
@Setter
public class AccountEntity extends BaseEntity {

    @Column(name = "title")
    private String title;

    @Column(name = "email")
    private String email;

    @Column(name = "balance")
    private BigDecimal balance;

}
