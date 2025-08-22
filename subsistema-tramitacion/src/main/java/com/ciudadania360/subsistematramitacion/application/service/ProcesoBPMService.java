package com.ciudadania360.subsistematramitacion.application.service;

import com.ciudadania360.subsistematramitacion.domain.entity.ProcesoBPM;
import com.ciudadania360.subsistematramitacion.domain.handler.ProcesoBPMHandler;
import com.ciudadania360.subsistematramitacion.application.dto.procesobpm.ProcesoBPMRequest;
import com.ciudadania360.subsistematramitacion.application.dto.procesobpm.ProcesoBPMResponse;
import com.ciudadania360.subsistematramitacion.application.mapper.ProcesoBPMMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProcesoBPMService {

    private final ProcesoBPMHandler handler;
    private final ProcesoBPMMapper mapper;

    public ProcesoBPMService(ProcesoBPMHandler handler, ProcesoBPMMapper mapper) {
        this.handler = handler;
        this.mapper = mapper;
    }

    public List<ProcesoBPMResponse> list() {
        return handler.list().stream().map(mapper::toResponse).toList();
    }

    public ProcesoBPMResponse get(UUID id) {
        return mapper.toResponse(handler.get(id));
    }

    public ProcesoBPMResponse create(ProcesoBPMRequest request) {
        ProcesoBPM entity = mapper.toEntity(request);
        entity.setId(UUID.randomUUID());
        return mapper.toResponse(handler.create(entity));
    }

    public ProcesoBPMResponse update(UUID id, ProcesoBPMRequest request) {
        ProcesoBPM existing = handler.get(id);
        mapper.updateEntity(existing, request);
        return mapper.toResponse(handler.update(id, existing));
    }

    public void delete(UUID id) {
        handler.delete(id);
    }
}
