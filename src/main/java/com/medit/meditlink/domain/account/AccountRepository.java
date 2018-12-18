package com.medit.meditlink.domain.account;

import com.medit.meditlink.domain.account.model.AccountEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AccountRepository extends PagingAndSortingRepository<AccountEntity, Long>, JpaSpecificationExecutor<AccountEntity> {

    boolean existsByEmail(String email);
    AccountEntity findByEmail(String email);
    
}