package com.ciudadania360.subsistemacomunicaciones.application.service;

import com.ciudadania360.subsistemacomunicaciones.application.dto.comunicacion.ComunicacionRequest;
import com.ciudadania360.subsistemacomunicaciones.application.dto.comunicacion.ComunicacionResponse;
import com.ciudadania360.subsistemacomunicaciones.application.mapper.ComunicacionMapper;
import com.ciudadania360.subsistemacomunicaciones.domain.entity.Comunicacion;
import com.ciudadania360.subsistemacomunicaciones.domain.handler.ComunicacionHandler;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ComunicacionService {

    private final ComunicacionHandler handler;
    private final ComunicacionMapper mapper;

    public ComunicacionService(ComunicacionHandler handler, ComunicacionMapper mapper) {
        this.handler = handler;
        this.mapper = mapper;
    }

    public List<ComunicacionResponse> list() {
        return handler.list().stream()
                .map(mapper::toResponse)
                .toList();
    }

    public ComunicacionResponse get(UUID id) {
        return mapper.toResponse(handler.get(id));
    }

    public ComunicacionResponse create(ComunicacionRequest request) {
        // MapStruct transforma el request en entity
        Comunicacion entity = mapper.toEntity(request);

        // Asignamos id y versión inicial
        entity.setId(UUID.randomUUID());
        entity.setVersion(0L);

        Comunicacion created = handler.create(entity);
        return mapper.toResponse(created);
    }

    public ComunicacionResponse update(UUID id, ComunicacionRequest request) {
        Comunicacion existing = handler.get(id);

        // Actualizamos parcialmente con los campos no nulos del request
        mapper.updateEntity(existing, request);

        Comunicacion updated = handler.update(id, existing);
        return mapper.toResponse(updated);
    }

    public void delete(UUID id) {
        handler.delete(id);
    }
}
