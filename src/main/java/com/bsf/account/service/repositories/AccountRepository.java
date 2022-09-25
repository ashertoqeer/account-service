package com.bsf.account.service.repositories;

import com.bsf.account.service.entities.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

    boolean existsByEmail(String email);

}
