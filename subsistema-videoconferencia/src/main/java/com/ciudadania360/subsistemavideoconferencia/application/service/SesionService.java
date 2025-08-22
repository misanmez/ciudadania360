package com.ciudadania360.subsistemavideoconferencia.application.service;

import com.ciudadania360.subsistemavideoconferencia.application.dto.sesion.SesionRequest;
import com.ciudadania360.subsistemavideoconferencia.application.dto.sesion.SesionResponse;
import com.ciudadania360.subsistemavideoconferencia.application.mapper.SesionMapper;
import com.ciudadania360.subsistemavideoconferencia.domain.entity.Sesion;
import com.ciudadania360.subsistemavideoconferencia.domain.handler.SesionHandler;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SesionService {

    private final SesionHandler handler;
    private final SesionMapper mapper;

    public SesionService(SesionHandler handler, SesionMapper mapper) {
        this.handler = handler;
        this.mapper = mapper;
    }

    public List<SesionResponse> list() {
        return handler.list().stream()
                .map(mapper::toResponse)
                .toList();
    }

    public SesionResponse get(UUID id) {
        return mapper.toResponse(handler.get(id));
    }

    public SesionResponse create(SesionRequest request) {
        Sesion entity = mapper.toEntity(request);
        entity.setId(UUID.randomUUID());
        entity.setVersion(0L);
        return mapper.toResponse(handler.create(entity));
    }

    public SesionResponse update(UUID id, SesionRequest request) {
        Sesion existing = handler.get(id);
        mapper.updateEntity(existing, request);
        return mapper.toResponse(handler.update(id, existing));
    }

    public void delete(UUID id) {
        handler.delete(id);
    }
}
