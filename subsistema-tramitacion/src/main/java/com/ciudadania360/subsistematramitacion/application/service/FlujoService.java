package com.ciudadania360.subsistematramitacion.application.service;

import com.ciudadania360.subsistematramitacion.domain.entity.Flujo;
import com.ciudadania360.subsistematramitacion.domain.handler.FlujoHandler;
import com.ciudadania360.subsistematramitacion.application.dto.flujo.FlujoRequest;
import com.ciudadania360.subsistematramitacion.application.dto.flujo.FlujoResponse;
import com.ciudadania360.subsistematramitacion.application.mapper.FlujoMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FlujoService {

    private final FlujoHandler handler;
    private final FlujoMapper mapper;

    public FlujoService(FlujoHandler handler, FlujoMapper mapper) {
        this.handler = handler;
        this.mapper = mapper;
    }

    public List<FlujoResponse> list() {
        return handler.list().stream().map(mapper::toResponse).toList();
    }

    public FlujoResponse get(UUID id) {
        return mapper.toResponse(handler.get(id));
    }

    public FlujoResponse create(FlujoRequest request) {
        Flujo entity = mapper.toEntity(request);
        entity.setId(UUID.randomUUID());
        entity.setVersion(0L);
        return mapper.toResponse(handler.create(entity));
    }

    public FlujoResponse update(UUID id, FlujoRequest request) {
        Flujo existing = handler.get(id);
        mapper.updateEntity(existing, request);
        return mapper.toResponse(handler.update(id, existing));
    }

    public void delete(UUID id) {
        handler.delete(id);
    }
}
