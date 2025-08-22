package com.ciudadania360.subsistemacomunicaciones.application.service;

import com.ciudadania360.subsistemacomunicaciones.application.dto.encuesta.EncuestaRequest;
import com.ciudadania360.subsistemacomunicaciones.application.dto.encuesta.EncuestaResponse;
import com.ciudadania360.subsistemacomunicaciones.application.mapper.EncuestaMapper;
import com.ciudadania360.subsistemacomunicaciones.domain.entity.Encuesta;
import com.ciudadania360.subsistemacomunicaciones.domain.handler.EncuestaHandler;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EncuestaService {

    private final EncuestaHandler handler;
    private final EncuestaMapper mapper;

    public EncuestaService(EncuestaHandler handler, EncuestaMapper mapper) {
        this.handler = handler;
        this.mapper = mapper;
    }

    public List<EncuestaResponse> list() {
        return handler.list().stream()
                .map(mapper::toResponse)
                .toList();
    }

    public EncuestaResponse get(UUID id) {
        return mapper.toResponse(handler.get(id));
    }

    public EncuestaResponse create(EncuestaRequest request) {
        // MapStruct transforma el request en entidad
        Encuesta entity = mapper.toEntity(request);

        // Asignamos id y versión inicial
        entity.setId(UUID.randomUUID());
        entity.setVersion(0L);

        Encuesta created = handler.create(entity);
        return mapper.toResponse(created);
    }

    public EncuestaResponse update(UUID id, EncuestaRequest request) {
        Encuesta existing = handler.get(id);

        // Actualizamos parcialmente con los campos no nulos
        mapper.updateEntity(existing, request);

        Encuesta updated = handler.update(id, existing);
        return mapper.toResponse(updated);
    }

    public void delete(UUID id) {
        handler.delete(id);
    }
}
