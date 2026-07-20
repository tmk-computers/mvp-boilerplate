package com.dasp.framework.modules.user.repository;

import com.dasp.framework.core.base.BaseRepository;
import com.dasp.framework.modules.user.domain.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends BaseRepository<User, UUID> {

    Optional<User> findByEmailAndIsDeletedFalse(String email);

    boolean existsByEmailAndIsDeletedFalse(String email);
}
