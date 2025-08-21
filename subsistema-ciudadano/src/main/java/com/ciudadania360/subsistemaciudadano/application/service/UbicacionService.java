package com.ciudadania360.subsistemaciudadano.application.service;

import com.ciudadania360.subsistemaciudadano.application.dto.UbicacionRequest;
import com.ciudadania360.subsistemaciudadano.application.dto.UbicacionResponse;
import com.ciudadania360.subsistemaciudadano.application.mapper.UbicacionMapper;
import com.ciudadania360.subsistemaciudadano.domain.entity.Ubicacion;
import com.ciudadania360.subsistemaciudadano.domain.handler.UbicacionHandler;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UbicacionService {

    private final UbicacionHandler handler;
    private final UbicacionMapper mapper;

    public UbicacionService(UbicacionHandler handler, UbicacionMapper mapper) {
        this.handler = handler;
        this.mapper = mapper;
    }

    public List<UbicacionResponse> list() {
        return handler.list().stream()
                .map(mapper::toResponse)
                .toList();
    }

    public UbicacionResponse get(UUID id) {
        Ubicacion e = handler.get(id);
        return mapper.toResponse(e);
    }

    public UbicacionResponse create(UbicacionRequest request) {
        Ubicacion entity = mapper.toEntity(request);
        Ubicacion created = handler.create(entity);
        return mapper.toResponse(created);
    }

    public UbicacionResponse update(UUID id, UbicacionRequest request) {
        Ubicacion existing = handler.get(id);
        mapper.updateEntity(existing, request); // MapStruct actualiza solo los campos no nulos
        Ubicacion updated = handler.update(id, existing);
        return mapper.toResponse(updated);
    }

    public void delete(UUID id) {
        handler.delete(id);
    }
}
