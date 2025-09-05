package com.ciudadania360.subsistemainterno.application.service;

import com.ciudadania360.subsistemainterno.application.dto.departamento.*;
import com.ciudadania360.subsistemainterno.application.mapper.DepartamentoMapper;
import com.ciudadania360.subsistemainterno.application.validator.DepartamentoValidator;
import com.ciudadania360.subsistemainterno.domain.entity.Departamento;
import com.ciudadania360.subsistemainterno.domain.handler.DepartamentoHandler;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DepartamentoService {

    private final DepartamentoHandler handler;
    private final DepartamentoMapper mapper;
    private final DepartamentoValidator validator;

    public DepartamentoService(DepartamentoHandler handler,
                               DepartamentoMapper mapper,
                               DepartamentoValidator validator) {
        this.handler = handler;
        this.mapper = mapper;
        this.validator = validator;
    }

    public List<DepartamentoResponse> list() {
        return handler.list().stream()
                .map(mapper::toResponse)
                .toList();
    }

    public DepartamentoResponse get(UUID id) {
        return mapper.toResponse(handler.get(id));
    }

    public DepartamentoResponse create(DepartamentoRequest request) {
        validator.validateForCreate(request);

        Departamento entity = mapper.toEntity(request);
        entity.setId(UUID.randomUUID());
        entity.setVersion(0L);

        Departamento created = handler.create(entity);
        return mapper.toResponse(created);
    }

    public DepartamentoResponse update(UUID id, DepartamentoRequest request) {
        validator.validateForUpdate(request);

        Departamento existing = handler.get(id);
        mapper.updateEntity(existing, request);

        Departamento updated = handler.update(id, existing);
        return mapper.toResponse(updated);
    }

    public void delete(UUID id) {
        handler.delete(id);
    }
}
