package com.ciudadania360.subsistematramitacion.application.service;

import com.ciudadania360.subsistematramitacion.domain.entity.Carpeta;
import com.ciudadania360.subsistematramitacion.domain.handler.CarpetaHandler;
import com.ciudadania360.subsistematramitacion.application.dto.carpeta.CarpetaRequest;
import com.ciudadania360.subsistematramitacion.application.dto.carpeta.CarpetaResponse;
import com.ciudadania360.subsistematramitacion.application.mapper.CarpetaMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CarpetaService {

    private final CarpetaHandler handler;
    private final CarpetaMapper mapper;

    public CarpetaService(CarpetaHandler handler, CarpetaMapper mapper) {
        this.handler = handler;
        this.mapper = mapper;
    }

    public List<CarpetaResponse> list() {
        return handler.list().stream()
                .map(mapper::toResponse)
                .toList();
    }

    public CarpetaResponse get(UUID id) {
        return mapper.toResponse(handler.get(id));
    }

    public CarpetaResponse create(CarpetaRequest request) {
        Carpeta entity = mapper.toEntity(request);
        entity.setId(UUID.randomUUID());
        entity.setVersion(0L);
        return mapper.toResponse(handler.create(entity));
    }

    public CarpetaResponse update(UUID id, CarpetaRequest request) {
        Carpeta existing = handler.get(id);
        mapper.updateEntity(existing, request);
        return mapper.toResponse(handler.update(id, existing));
    }

    public void delete(UUID id) {
        handler.delete(id);
    }
}
