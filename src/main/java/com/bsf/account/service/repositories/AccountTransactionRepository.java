package com.bsf.account.service.repositories;

import com.bsf.account.service.entities.AccountTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountTransactionRepository extends JpaRepository<AccountTransactionEntity, Long> {

}
