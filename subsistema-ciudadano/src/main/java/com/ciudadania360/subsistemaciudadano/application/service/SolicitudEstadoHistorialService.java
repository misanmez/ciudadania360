package com.ciudadania360.subsistemaciudadano.application.service;

import com.ciudadania360.subsistemaciudadano.application.dto.solicitudestadohistorial.SolicitudEstadoHistorialRequest;
import com.ciudadania360.subsistemaciudadano.application.dto.solicitudestadohistorial.SolicitudEstadoHistorialResponse;
import com.ciudadania360.subsistemaciudadano.application.mapper.SolicitudEstadoHistorialMapper;
import com.ciudadania360.subsistemaciudadano.application.validator.SolicitudEstadoHistorialValidator;
import com.ciudadania360.subsistemaciudadano.domain.entity.SolicitudEstadoHistorial;
import com.ciudadania360.subsistemaciudadano.domain.handler.SolicitudEstadoHistorialHandler;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SolicitudEstadoHistorialService {

    private final SolicitudEstadoHistorialHandler handler;
    private final SolicitudEstadoHistorialMapper mapper;
    private final SolicitudEstadoHistorialValidator validator;

    public SolicitudEstadoHistorialService(SolicitudEstadoHistorialHandler handler,
                                           SolicitudEstadoHistorialMapper mapper,
                                           SolicitudEstadoHistorialValidator validator) {
        this.handler = handler;
        this.mapper = mapper;
        this.validator = validator;
    }

    public List<SolicitudEstadoHistorialResponse> list() {
        return handler.list().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    public SolicitudEstadoHistorialResponse get(UUID id) {
        SolicitudEstadoHistorial entity = handler.get(id);
        return mapper.toResponse(entity);
    }

    public SolicitudEstadoHistorialResponse create(SolicitudEstadoHistorialRequest request) {
        validator.validateCreate(request);

        SolicitudEstadoHistorial entity = mapper.toEntity(request);
        entity.setId(UUID.randomUUID());

        SolicitudEstadoHistorial created = handler.create(entity);
        return mapper.toResponse(created);
    }

    public SolicitudEstadoHistorialResponse update(UUID id, SolicitudEstadoHistorialRequest request) {
        validator.validateUpdate(request);

        SolicitudEstadoHistorial existing = handler.get(id);
        mapper.updateEntity(existing, request); // MapStruct actualiza solo los campos no nulos

        SolicitudEstadoHistorial updated = handler.update(id, existing);
        return mapper.toResponse(updated);
    }

    public void delete(UUID id) {
        SolicitudEstadoHistorial entity = handler.get(id);
        validator.validateDelete(entity);
        handler.delete(id);
    }
}
