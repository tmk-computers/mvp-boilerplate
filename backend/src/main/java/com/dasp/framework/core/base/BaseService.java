package com.dasp.framework.core.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

public interface BaseService<ReqDTO, ResDTO, ID extends Serializable> {

    ResDTO create(ReqDTO request);

    ResDTO update(ID id, ReqDTO request);

    ResDTO findById(ID id);

    Page<ResDTO> findAll(Pageable pageable);

    List<ResDTO> findAll();

    void softDelete(ID id);
}
