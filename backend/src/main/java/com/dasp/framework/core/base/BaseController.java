package com.dasp.framework.core.base;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

public abstract class BaseController<ReqDTO, ResDTO, ID extends Serializable> {

    protected final BaseService<ReqDTO, ResDTO, ID> service;

    protected BaseController(BaseService<ReqDTO, ResDTO, ID> service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<BaseResponse<ResDTO>> create(@Valid @RequestBody ReqDTO request) {
        ResDTO created = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponse.success(created, "Resource created successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<ResDTO>> update(@PathVariable ID id, @Valid @RequestBody ReqDTO request) {
        ResDTO updated = service.update(id, request);
        return ResponseEntity.ok(BaseResponse.success(updated, "Resource updated successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<ResDTO>> getById(@PathVariable ID id) {
        ResDTO dto = service.findById(id);
        return ResponseEntity.ok(BaseResponse.success(dto));
    }

    @GetMapping
    public ResponseEntity<BaseResponse<Page<ResDTO>>> getAll(Pageable pageable) {
        Page<ResDTO> page = service.findAll(pageable);
        return ResponseEntity.ok(BaseResponse.success(page));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> delete(@PathVariable ID id) {
        service.softDelete(id);
        return ResponseEntity.ok(BaseResponse.success(null, "Resource deleted successfully"));
    }
}
