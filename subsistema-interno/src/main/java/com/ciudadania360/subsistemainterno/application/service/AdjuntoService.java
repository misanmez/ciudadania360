package com.ciudadania360.subsistemainterno.application.service;

import com.ciudadania360.subsistemainterno.application.dto.adjunto.*;
import com.ciudadania360.subsistemainterno.application.mapper.AdjuntoMapper;
import com.ciudadania360.subsistemainterno.application.validator.AdjuntoValidator;
import com.ciudadania360.subsistemainterno.domain.entity.Adjunto;
import com.ciudadania360.subsistemainterno.domain.handler.AdjuntoHandler;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AdjuntoService {

    private final AdjuntoHandler handler;
    private final AdjuntoMapper mapper;
    private final AdjuntoValidator validator;

    public AdjuntoService(AdjuntoHandler handler,
                          AdjuntoMapper mapper,
                          AdjuntoValidator validator) {
        this.handler = handler;
        this.mapper = mapper;
        this.validator = validator;
    }

    public List<AdjuntoResponse> list() {
        return handler.list().stream()
                .map(mapper::toResponse)
                .toList();
    }

    public AdjuntoResponse get(UUID id) {
        return mapper.toResponse(handler.get(id));
    }

    public AdjuntoResponse create(AdjuntoRequest request) {
        validator.validateForCreate(request);

        Adjunto entity = mapper.toEntity(request);
        entity.setId(UUID.randomUUID());
        entity.setVersion(0L);

        Adjunto created = handler.create(entity);
        return mapper.toResponse(created);
    }

    public AdjuntoResponse update(UUID id, AdjuntoRequest request) {
        validator.validateForUpdate(request);

        Adjunto existing = handler.get(id);
        mapper.updateEntity(existing, request);

        Adjunto updated = handler.update(id, existing);
        return mapper.toResponse(updated);
    }

    public void delete(UUID id) {
        handler.delete(id);
    }
}
