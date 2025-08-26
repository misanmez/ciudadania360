package com.ciudadania360.subsistemaciudadano.application.service;

import com.ciudadania360.subsistemaciudadano.application.dto.direccion.DireccionRequest;
import com.ciudadania360.subsistemaciudadano.application.dto.direccion.DireccionResponse;
import com.ciudadania360.subsistemaciudadano.application.mapper.DireccionMapper;
import com.ciudadania360.subsistemaciudadano.domain.entity.Direccion;
import com.ciudadania360.subsistemaciudadano.domain.handler.DireccionHandler;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DireccionService {

    private final DireccionHandler handler;
    private final DireccionMapper mapper;

    public DireccionService(DireccionHandler handler, DireccionMapper mapper) {
        this.handler = handler;
        this.mapper = mapper;
    }

    public List<DireccionResponse> list() {
        return handler.list().stream()
                .map(mapper::toResponse)
                .toList();
    }

    public DireccionResponse get(UUID id) {
        Direccion d = handler.get(id);
        return mapper.toResponse(d);
    }

    public DireccionResponse create(DireccionRequest request) {
        Direccion entity = mapper.toEntity(request);
        entity.setId(UUID.randomUUID());
        Direccion created = handler.create(entity);
        return mapper.toResponse(created);
    }

    public DireccionResponse update(UUID id, DireccionRequest request) {
        Direccion existing = handler.get(id);
        mapper.updateEntity(existing, request); // MapStruct actualiza solo los campos no nulos
        Direccion updated = handler.update(id, existing);
        return mapper.toResponse(updated);
    }

    public void delete(UUID id) {
        handler.delete(id);
    }
}
