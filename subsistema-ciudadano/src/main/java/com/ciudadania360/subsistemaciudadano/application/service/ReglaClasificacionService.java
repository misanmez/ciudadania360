package com.ciudadania360.subsistemaciudadano.application.service;

import com.ciudadania360.subsistemaciudadano.application.dto.reglaclasificacion.ReglaClasificacionRequest;
import com.ciudadania360.subsistemaciudadano.application.dto.reglaclasificacion.ReglaClasificacionResponse;
import com.ciudadania360.subsistemaciudadano.application.mapper.ReglaClasificacionMapper;
import com.ciudadania360.subsistemaciudadano.domain.entity.ReglaClasificacion;
import com.ciudadania360.subsistemaciudadano.domain.handler.ReglaClasificacionHandler;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ReglaClasificacionService {

    private final ReglaClasificacionHandler handler;
    private final ReglaClasificacionMapper mapper;

    public ReglaClasificacionService(ReglaClasificacionHandler handler, ReglaClasificacionMapper mapper) {
        this.handler = handler;
        this.mapper = mapper;
    }

    public List<ReglaClasificacionResponse> list() {
        return handler.list().stream()
                .map(mapper::toResponse)
                .toList();
    }

    public ReglaClasificacionResponse get(UUID id) {
        ReglaClasificacion e = handler.get(id);
        return mapper.toResponse(e);
    }

    public ReglaClasificacionResponse create(ReglaClasificacionRequest request) {
        ReglaClasificacion entity = mapper.toEntity(request);
        ReglaClasificacion created = handler.create(entity);
        return mapper.toResponse(created);
    }

    public ReglaClasificacionResponse update(UUID id, ReglaClasificacionRequest request) {
        ReglaClasificacion existing = handler.get(id);
        mapper.updateEntity(existing, request); // MapStruct actualiza solo los campos no nulos
        ReglaClasificacion updated = handler.update(id, existing);
        return mapper.toResponse(updated);
    }

    public void delete(UUID id) {
        handler.delete(id);
    }
}
