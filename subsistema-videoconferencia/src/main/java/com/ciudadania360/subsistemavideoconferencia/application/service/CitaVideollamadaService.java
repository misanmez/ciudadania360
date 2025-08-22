package com.ciudadania360.subsistemavideoconferencia.application.service;

import com.ciudadania360.subsistemavideoconferencia.application.dto.citavideollamada.CitaVideollamadaRequest;
import com.ciudadania360.subsistemavideoconferencia.application.dto.citavideollamada.CitaVideollamadaResponse;
import com.ciudadania360.subsistemavideoconferencia.application.mapper.CitaVideollamadaMapper;
import com.ciudadania360.subsistemavideoconferencia.domain.entity.CitaVideollamada;
import com.ciudadania360.subsistemavideoconferencia.domain.handler.CitaVideollamadaHandler;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CitaVideollamadaService {

    private final CitaVideollamadaHandler handler;
    private final CitaVideollamadaMapper mapper;

    public CitaVideollamadaService(CitaVideollamadaHandler handler, CitaVideollamadaMapper mapper) {
        this.handler = handler;
        this.mapper = mapper;
    }

    public List<CitaVideollamadaResponse> list() {
        return handler.list().stream()
                .map(mapper::toResponse)
                .toList();
    }

    public CitaVideollamadaResponse get(UUID id) {
        return mapper.toResponse(handler.get(id));
    }

    public CitaVideollamadaResponse create(CitaVideollamadaRequest request) {
        CitaVideollamada entity = mapper.toEntity(request);
        entity.setId(UUID.randomUUID());
        entity.setVersion(0L);
        return mapper.toResponse(handler.create(entity));
    }

    public CitaVideollamadaResponse update(UUID id, CitaVideollamadaRequest request) {
        CitaVideollamada existing = handler.get(id);
        mapper.updateEntity(existing, request);
        return mapper.toResponse(handler.update(id, existing));
    }

    public void delete(UUID id) {
        handler.delete(id);
    }
}
