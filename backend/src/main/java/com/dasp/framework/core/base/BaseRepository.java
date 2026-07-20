package com.dasp.framework.core.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity, ID extends Serializable> extends JpaRepository<T, ID> {

    Optional<T> findByIdAndIsDeletedFalse(ID id);

    Page<T> findAllByIsDeletedFalse(Pageable pageable);

    List<T> findAllByIsDeletedFalse();
}
