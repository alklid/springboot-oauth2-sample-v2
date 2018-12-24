package com.alklid.oauth.domain.user;

import com.alklid.oauth.domain.user.model.UserEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long>, JpaSpecificationExecutor<UserEntity> {

    boolean existsByEmail(String email);
    UserEntity findByEmail(String email);
    
}