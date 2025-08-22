package com.ciudadania360.subsistematramitacion.application.service;

import com.ciudadania360.subsistematramitacion.domain.entity.Integracion;
import com.ciudadania360.subsistematramitacion.domain.handler.IntegracionHandler;
import com.ciudadania360.subsistematramitacion.application.dto.integracion.IntegracionRequest;
import com.ciudadania360.subsistematramitacion.application.dto.integracion.IntegracionResponse;
import com.ciudadania360.subsistematramitacion.application.mapper.IntegracionMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class IntegracionService {

    private final IntegracionHandler handler;
    private final IntegracionMapper mapper;

    public IntegracionService(IntegracionHandler handler, IntegracionMapper mapper) {
        this.handler = handler;
        this.mapper = mapper;
    }

    public List<IntegracionResponse> list() {
        return handler.list().stream().map(mapper::toResponse).toList();
    }

    public IntegracionResponse get(UUID id) {
        return mapper.toResponse(handler.get(id));
    }

    public IntegracionResponse create(IntegracionRequest request) {
        Integracion entity = mapper.toEntity(request);
        entity.setId(UUID.randomUUID());
        return mapper.toResponse(handler.create(entity));
    }

    public IntegracionResponse update(UUID id, IntegracionRequest request) {
        Integracion existing = handler.get(id);
        mapper.updateEntity(existing, request);
        return mapper.toResponse(handler.update(id, existing));
    }

    public void delete(UUID id) {
        handler.delete(id);
    }
}
