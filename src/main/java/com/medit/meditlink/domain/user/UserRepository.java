package com.medit.meditlink.domain.user;

import com.medit.meditlink.domain.user.model.UserEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long>, JpaSpecificationExecutor<UserEntity> {

    boolean existsByEmail(String email);
    UserEntity findByEmail(String email);
    
}