package com.ciudadania360.subsistematramitacion.application.service;

import com.ciudadania360.subsistematramitacion.domain.entity.Contrata;
import com.ciudadania360.subsistematramitacion.domain.handler.ContrataHandler;
import com.ciudadania360.subsistematramitacion.application.dto.contrata.ContrataRequest;
import com.ciudadania360.subsistematramitacion.application.dto.contrata.ContrataResponse;
import com.ciudadania360.subsistematramitacion.application.mapper.ContrataMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ContrataService {

    private final ContrataHandler handler;
    private final ContrataMapper mapper;

    public ContrataService(ContrataHandler handler, ContrataMapper mapper) {
        this.handler = handler;
        this.mapper = mapper;
    }

    public List<ContrataResponse> list() {
        return handler.list().stream()
                .map(mapper::toResponse)
                .toList();
    }

    public ContrataResponse get(UUID id) {
        return mapper.toResponse(handler.get(id));
    }

    public ContrataResponse create(ContrataRequest request) {
        Contrata entity = mapper.toEntity(request);
        entity.setId(UUID.randomUUID());
        return mapper.toResponse(handler.create(entity));
    }

    public ContrataResponse update(UUID id, ContrataRequest request) {
        Contrata existing = handler.get(id);
        mapper.updateEntity(existing, request);
        return mapper.toResponse(handler.update(id, existing));
    }

    public void delete(UUID id) {
        handler.delete(id);
    }
}
