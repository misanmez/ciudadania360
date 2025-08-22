package com.ciudadania360.subsistemainformacion.application.service;

import com.ciudadania360.subsistemainformacion.domain.entity.Informacion;
import com.ciudadania360.subsistemainformacion.domain.handler.InformacionHandler;
import com.ciudadania360.subsistemainformacion.application.mapper.InformacionMapper;
import com.ciudadania360.subsistemainformacion.application.dto.informacion.InformacionRequest;
import com.ciudadania360.subsistemainformacion.application.dto.informacion.InformacionResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class InformacionService {

    private final InformacionHandler handler;
    private final InformacionMapper mapper;

    public InformacionService(InformacionHandler handler, InformacionMapper mapper) {
        this.handler = handler;
        this.mapper = mapper;
    }

    public List<InformacionResponse> list() {
        return handler.list().stream().map(mapper::toResponse).toList();
    }

    public InformacionResponse get(UUID id) {
        return mapper.toResponse(handler.get(id));
    }

    public InformacionResponse create(InformacionRequest request) {
        Informacion entity = mapper.toEntity(request);
        entity.setId(UUID.randomUUID());
        entity.setVersion(0L);
        return mapper.toResponse(handler.create(entity));
    }

    public InformacionResponse update(UUID id, InformacionRequest request) {
        Informacion existing = handler.get(id);
        mapper.updateEntity(existing, request);
        return mapper.toResponse(handler.update(id, existing));
    }

    public void delete(UUID id) {
        handler.delete(id);
    }
}
