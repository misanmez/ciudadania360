package com.ciudadania360.subsistemaciudadano.application.service;

import com.ciudadania360.subsistemaciudadano.application.dto.encuesta.EncuestaRequest;
import com.ciudadania360.subsistemaciudadano.application.dto.encuesta.EncuestaResponse;
import com.ciudadania360.subsistemaciudadano.application.mapper.EncuestaMapper;
import com.ciudadania360.subsistemaciudadano.application.validator.EncuestaValidator;
import com.ciudadania360.subsistemaciudadano.domain.entity.Encuesta;
import com.ciudadania360.subsistemaciudadano.domain.handler.EncuestaHandler;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EncuestaService {

    private final EncuestaHandler handler;
    private final EncuestaMapper mapper;
    private final EncuestaValidator validator;

    public EncuestaService(EncuestaHandler handler, EncuestaMapper mapper, EncuestaValidator validator) {
        this.handler = handler;
        this.mapper = mapper;
        this.validator = validator;
    }

    public List<EncuestaResponse> list() {
        return handler.list().stream()
                .map(mapper::toResponse)
                .toList();
    }

    public EncuestaResponse get(UUID id) {
        Encuesta e = handler.get(id);
        return mapper.toResponse(e);
    }

    public EncuestaResponse create(EncuestaRequest request) {
        validator.validateCreate(request);

        Encuesta entity = mapper.toEntity(request);
        entity.setId(UUID.randomUUID());
        entity.setVersion(0L);

        Encuesta created = handler.create(entity);
        return mapper.toResponse(created);
    }

    public EncuestaResponse update(UUID id, EncuestaRequest request) {
        validator.validateUpdate(request);

        Encuesta existing = handler.get(id);
        mapper.updateEntity(existing, request);

        Encuesta updated = handler.update(id, existing);
        return mapper.toResponse(updated);
    }

    public void delete(UUID id) {
        boolean exists = handler.exists(id);
        validator.validateDelete(id, exists);
        handler.delete(id);
    }
}
