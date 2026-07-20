package com.dasp.framework.core.base;

import java.util.List;

public interface BaseMapper<E, ReqDTO, ResDTO> {

    E toEntity(ReqDTO dto);

    ResDTO toDto(E entity);

    List<E> toEntityList(List<ReqDTO> dtoList);

    List<ResDTO> toDtoList(List<E> entityList);
}
