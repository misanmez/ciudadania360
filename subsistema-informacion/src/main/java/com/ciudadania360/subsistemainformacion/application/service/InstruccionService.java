package com.ciudadania360.subsistemainformacion.application.service;

import com.ciudadania360.subsistemainformacion.domain.entity.Instruccion;
import com.ciudadania360.subsistemainformacion.domain.handler.InstruccionHandler;
import com.ciudadania360.subsistemainformacion.application.mapper.InstruccionMapper;
import com.ciudadania360.subsistemainformacion.application.dto.instruccion.InstruccionRequest;
import com.ciudadania360.subsistemainformacion.application.dto.instruccion.InstruccionResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class InstruccionService {

    private final InstruccionHandler handler;
    private final InstruccionMapper mapper;

    public InstruccionService(InstruccionHandler handler, InstruccionMapper mapper) {
        this.handler = handler;
        this.mapper = mapper;
    }

    public List<InstruccionResponse> list() {
        return handler.list().stream().map(mapper::toResponse).toList();
    }

    public InstruccionResponse get(UUID id) {
        return mapper.toResponse(handler.get(id));
    }

    public InstruccionResponse create(InstruccionRequest request) {
        Instruccion entity = mapper.toEntity(request);
        entity.setId(UUID.randomUUID());
        return mapper.toResponse(handler.create(entity));
    }

    public InstruccionResponse update(UUID id, InstruccionRequest request) {
        Instruccion existing = handler.get(id);
        mapper.updateEntity(existing, request);
        return mapper.toResponse(handler.update(id, existing));
    }

    public void delete(UUID id) {
        handler.delete(id);
    }
}
