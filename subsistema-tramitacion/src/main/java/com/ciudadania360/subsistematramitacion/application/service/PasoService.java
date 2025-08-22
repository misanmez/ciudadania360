package com.ciudadania360.subsistematramitacion.application.service;

import com.ciudadania360.subsistematramitacion.domain.entity.Paso;
import com.ciudadania360.subsistematramitacion.domain.handler.PasoHandler;
import com.ciudadania360.subsistematramitacion.application.dto.paso.PasoRequest;
import com.ciudadania360.subsistematramitacion.application.dto.paso.PasoResponse;
import com.ciudadania360.subsistematramitacion.application.mapper.PasoMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PasoService {

    private final PasoHandler handler;
    private final PasoMapper mapper;

    public PasoService(PasoHandler handler, PasoMapper mapper) {
        this.handler = handler;
        this.mapper = mapper;
    }

    public List<PasoResponse> list() {
        return handler.list().stream().map(mapper::toResponse).toList();
    }

    public PasoResponse get(UUID id) {
        return mapper.toResponse(handler.get(id));
    }

    public PasoResponse create(PasoRequest request) {
        Paso entity = mapper.toEntity(request);
        entity.setId(UUID.randomUUID());
        return mapper.toResponse(handler.create(entity));
    }

    public PasoResponse update(UUID id, PasoRequest request) {
        Paso existing = handler.get(id);
        mapper.updateEntity(existing, request);
        return mapper.toResponse(handler.update(id, existing));
    }

    public void delete(UUID id) {
        handler.delete(id);
    }
}
