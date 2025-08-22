package com.ciudadania360.subsistemacomunicaciones.application.service;

import com.ciudadania360.subsistemacomunicaciones.application.dto.suscripcion.SuscripcionRequest;
import com.ciudadania360.subsistemacomunicaciones.application.dto.suscripcion.SuscripcionResponse;
import com.ciudadania360.subsistemacomunicaciones.application.mapper.SuscripcionMapper;
import com.ciudadania360.subsistemacomunicaciones.domain.entity.Suscripcion;
import com.ciudadania360.subsistemacomunicaciones.domain.handler.SuscripcionHandler;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SuscripcionService {

    private final SuscripcionHandler handler;
    private final SuscripcionMapper mapper;

    public SuscripcionService(SuscripcionHandler handler, SuscripcionMapper mapper) {
        this.handler = handler;
        this.mapper = mapper;
    }

    public List<SuscripcionResponse> list() {
        return handler.list().stream()
                .map(mapper::toResponse)
                .toList();
    }

    public SuscripcionResponse get(UUID id) {
        return mapper.toResponse(handler.get(id));
    }

    public SuscripcionResponse create(SuscripcionRequest request) {
        Suscripcion entity = mapper.toEntity(request);

        entity.setId(UUID.randomUUID());
        entity.setVersion(0L);

        Suscripcion created = handler.create(entity);
        return mapper.toResponse(created);
    }

    public SuscripcionResponse update(UUID id, SuscripcionRequest request) {
        Suscripcion existing = handler.get(id);

        mapper.updateEntity(existing, request);

        Suscripcion updated = handler.update(id, existing);
        return mapper.toResponse(updated);
    }

    public void delete(UUID id) {
        handler.delete(id);
    }
}
