package com.ciudadania360.subsistemaciudadano.application.service;

import com.ciudadania360.subsistemaciudadano.application.dto.SolicitudRequest;
import com.ciudadania360.subsistemaciudadano.application.dto.SolicitudResponse;
import com.ciudadania360.subsistemaciudadano.application.mapper.SolicitudMapper;
import com.ciudadania360.subsistemaciudadano.domain.entity.Solicitud;
import com.ciudadania360.subsistemaciudadano.domain.handler.SolicitudHandler;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SolicitudService {

    private final SolicitudHandler handler;
    private final SolicitudMapper mapper;

    public SolicitudService(SolicitudHandler handler, SolicitudMapper mapper) {
        this.handler = handler;
        this.mapper = mapper;
    }

    public List<SolicitudResponse> list() {
        return handler.list().stream()
                .map(mapper::toResponse)
                .toList();
    }

    public SolicitudResponse get(UUID id) {
        Solicitud e = handler.get(id);
        return mapper.toResponse(e);
    }

    public SolicitudResponse create(SolicitudRequest request) {
        Solicitud entity = mapper.toEntity(request);
        Solicitud created = handler.create(entity);
        return mapper.toResponse(created);
    }

    public SolicitudResponse update(UUID id, SolicitudRequest request) {
        Solicitud existing = handler.get(id);
        mapper.updateEntity(existing, request); // MapStruct actualiza solo los campos no nulos
        Solicitud updated = handler.update(id, existing);
        return mapper.toResponse(updated);
    }

    public void delete(UUID id) {
        handler.delete(id);
    }
}
