package com.ciudadania360.subsistematramitacion.application.service;

import com.ciudadania360.subsistematramitacion.domain.entity.IncidenciaContrata;
import com.ciudadania360.subsistematramitacion.domain.handler.IncidenciaContrataHandler;
import com.ciudadania360.subsistematramitacion.application.dto.incidenciacontrata.IncidenciaContrataRequest;
import com.ciudadania360.subsistematramitacion.application.dto.incidenciacontrata.IncidenciaContrataResponse;
import com.ciudadania360.subsistematramitacion.application.mapper.IncidenciaContrataMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class IncidenciaContrataService {

    private final IncidenciaContrataHandler handler;
    private final IncidenciaContrataMapper mapper;

    public IncidenciaContrataService(IncidenciaContrataHandler handler, IncidenciaContrataMapper mapper) {
        this.handler = handler;
        this.mapper = mapper;
    }

    public List<IncidenciaContrataResponse> list() {
        return handler.list().stream().map(mapper::toResponse).toList();
    }

    public IncidenciaContrataResponse get(UUID id) {
        return mapper.toResponse(handler.get(id));
    }

    public IncidenciaContrataResponse create(IncidenciaContrataRequest request) {
        IncidenciaContrata entity = mapper.toEntity(request);
        entity.setId(UUID.randomUUID());
        return mapper.toResponse(handler.create(entity));
    }

    public IncidenciaContrataResponse update(UUID id, IncidenciaContrataRequest request) {
        IncidenciaContrata existing = handler.get(id);
        mapper.updateEntity(existing, request);
        return mapper.toResponse(handler.update(id, existing));
    }

    public void delete(UUID id) {
        handler.delete(id);
    }
}
