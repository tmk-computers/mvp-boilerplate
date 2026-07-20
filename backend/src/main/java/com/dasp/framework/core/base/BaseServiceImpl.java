package com.dasp.framework.core.base;

import com.dasp.framework.core.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Transactional
public abstract class BaseServiceImpl<E extends BaseAuditEntity, ReqDTO, ResDTO, ID extends Serializable>
        implements BaseService<ReqDTO, ResDTO, ID> {

    protected final BaseRepository<E, ID> repository;
    protected final BaseMapper<E, ReqDTO, ResDTO> mapper;

    protected BaseServiceImpl(BaseRepository<E, ID> repository, BaseMapper<E, ReqDTO, ResDTO> mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public ResDTO create(ReqDTO request) {
        E entity = mapper.toEntity(request);
        entity.setIsDeleted(false);
        E saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    @Override
    public ResDTO update(ID id, ReqDTO request) {
        E existing = repository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found with ID: " + id));
        updateEntityFromDto(request, existing);
        E updated = repository.save(existing);
        return mapper.toDto(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public ResDTO findById(ID id) {
        E entity = repository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found with ID: " + id));
        return mapper.toDto(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ResDTO> findAll(Pageable pageable) {
        return repository.findAllByIsDeletedFalse(pageable).map(mapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResDTO> findAll() {
        return mapper.toDtoList(repository.findAllByIsDeletedFalse());
    }

    @Override
    public void softDelete(ID id) {
        E entity = repository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found with ID: " + id));
        entity.setIsDeleted(true);
        entity.setDeletedDate(LocalDateTime.now());
        repository.save(entity);
    }

    protected abstract void updateEntityFromDto(ReqDTO dto, E entity);
}
