package com.ciudadania360.subsistemainformacion.application.service;

import com.ciudadania360.subsistemainformacion.domain.entity.Sugerencia;
import com.ciudadania360.subsistemainformacion.domain.handler.SugerenciaHandler;
import com.ciudadania360.subsistemainformacion.application.mapper.SugerenciaMapper;
import com.ciudadania360.subsistemainformacion.application.dto.sugerencia.SugerenciaRequest;
import com.ciudadania360.subsistemainformacion.application.dto.sugerencia.SugerenciaResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SugerenciaService {

    private final SugerenciaHandler handler;
    private final SugerenciaMapper mapper;

    public SugerenciaService(SugerenciaHandler handler, SugerenciaMapper mapper) {
        this.handler = handler;
        this.mapper = mapper;
    }

    public List<SugerenciaResponse> list() {
        return handler.list().stream().map(mapper::toResponse).toList();
    }

    public SugerenciaResponse get(UUID id) {
        return mapper.toResponse(handler.get(id));
    }

    public SugerenciaResponse create(SugerenciaRequest request) {
        Sugerencia entity = mapper.toEntity(request);
        entity.setId(UUID.randomUUID());
        entity.setVersion(0L);
        return mapper.toResponse(handler.create(entity));
    }

    public SugerenciaResponse update(UUID id, SugerenciaRequest request) {
        Sugerencia existing = handler.get(id);
        mapper.updateEntity(existing, request);
        return mapper.toResponse(handler.update(id, existing));
    }

    public void delete(UUID id) {
        handler.delete(id);
    }
}
