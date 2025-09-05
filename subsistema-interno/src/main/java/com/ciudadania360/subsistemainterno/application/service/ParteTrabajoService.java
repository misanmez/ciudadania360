package com.ciudadania360.subsistemainterno.application.service;

import com.ciudadania360.subsistemainterno.application.dto.partetrabajo.*;
import com.ciudadania360.subsistemainterno.application.mapper.ParteTrabajoMapper;
import com.ciudadania360.subsistemainterno.application.validator.ParteTrabajoValidator;
import com.ciudadania360.subsistemainterno.domain.entity.ParteTrabajo;
import com.ciudadania360.subsistemainterno.domain.handler.ParteTrabajoHandler;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ParteTrabajoService {

    private final ParteTrabajoHandler handler;
    private final ParteTrabajoMapper mapper;
    private final ParteTrabajoValidator validator;

    public ParteTrabajoService(ParteTrabajoHandler handler,
                               ParteTrabajoMapper mapper,
                               ParteTrabajoValidator validator) {
        this.handler = handler;
        this.mapper = mapper;
        this.validator = validator;
    }

    public List<ParteTrabajoResponse> list() {
        return handler.list().stream()
                .map(mapper::toResponse)
                .toList();
    }

    public ParteTrabajoResponse get(UUID id) {
        return mapper.toResponse(handler.get(id));
    }

    public ParteTrabajoResponse create(ParteTrabajoRequest request) {
        validator.validateForCreate(request);

        ParteTrabajo entity = mapper.toEntity(request);
        entity.setId(UUID.randomUUID());
        entity.setVersion(0L);

        ParteTrabajo created = handler.create(entity);
        return mapper.toResponse(created);
    }

    public ParteTrabajoResponse update(UUID id, ParteTrabajoRequest request) {
        ParteTrabajo existing = handler.get(id);
        validator.validateForUpdate(request, existing);

        mapper.updateEntity(existing, request);
        ParteTrabajo updated = handler.update(id, existing);
        return mapper.toResponse(updated);
    }

    public void delete(UUID id) {
        ParteTrabajo existing = handler.get(id);
        validator.validateForDelete(existing);
        handler.delete(id);
    }
}
