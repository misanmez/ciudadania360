package com.ciudadania360.subsistemaciudadano.application.service;

import com.ciudadania360.subsistemaciudadano.application.dto.interaccion.InteraccionRequest;
import com.ciudadania360.subsistemaciudadano.application.dto.interaccion.InteraccionResponse;
import com.ciudadania360.subsistemaciudadano.application.mapper.InteraccionMapper;
import com.ciudadania360.subsistemaciudadano.domain.entity.Interaccion;
import com.ciudadania360.subsistemaciudadano.domain.handler.InteraccionHandler;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class InteraccionService {

    private final InteraccionHandler handler;
    private final InteraccionMapper mapper;

    public InteraccionService(InteraccionHandler handler, InteraccionMapper mapper) {
        this.handler = handler;
        this.mapper = mapper;
    }

    public List<InteraccionResponse> list() {
        return handler.list().stream()
                .map(mapper::toResponse)
                .toList();
    }

    public InteraccionResponse get(UUID id) {
        Interaccion e = handler.get(id);
        return mapper.toResponse(e);
    }

    public InteraccionResponse create(InteraccionRequest request) {
        Interaccion entity = mapper.toEntity(request);
        Interaccion created = handler.create(entity);
        return mapper.toResponse(created);
    }

    public InteraccionResponse update(UUID id, InteraccionRequest request) {
        Interaccion existing = handler.get(id);
        mapper.updateEntity(existing, request); // MapStruct actualiza solo los campos no nulos
        Interaccion updated = handler.update(id, existing);
        return mapper.toResponse(updated);
    }

    public void delete(UUID id) {
        handler.delete(id);
    }
}
