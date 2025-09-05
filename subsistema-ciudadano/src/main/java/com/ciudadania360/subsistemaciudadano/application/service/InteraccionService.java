package com.ciudadania360.subsistemaciudadano.application.service;

import com.ciudadania360.subsistemaciudadano.application.dto.interaccion.InteraccionRequest;
import com.ciudadania360.subsistemaciudadano.application.dto.interaccion.InteraccionResponse;
import com.ciudadania360.subsistemaciudadano.application.mapper.InteraccionMapper;
import com.ciudadania360.subsistemaciudadano.application.validator.InteraccionValidator;
import com.ciudadania360.subsistemaciudadano.domain.entity.Interaccion;
import com.ciudadania360.subsistemaciudadano.domain.handler.InteraccionHandler;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class InteraccionService {

    private final InteraccionHandler handler;
    private final InteraccionMapper mapper;
    private final InteraccionValidator validator;

    public InteraccionService(InteraccionHandler handler, InteraccionMapper mapper, InteraccionValidator validator) {
        this.handler = handler;
        this.mapper = mapper;
        this.validator = validator;
    }

    public List<InteraccionResponse> list() {
        return handler.list().stream()
                .map(mapper::toResponse)
                .toList();
    }

    public InteraccionResponse get(UUID id) {
        return mapper.toResponse(handler.get(id));
    }

    public InteraccionResponse create(InteraccionRequest request) {
        validator.validateForCreate(request);

        Interaccion entity = mapper.toEntity(request);
        entity.setId(UUID.randomUUID());

        Interaccion created = handler.create(entity);
        return mapper.toResponse(created);
    }

    public InteraccionResponse update(UUID id, InteraccionRequest request) {
        Interaccion existing = handler.get(id);
        validator.validateForUpdate(request, existing);

        mapper.updateEntity(existing, request);
        Interaccion updated = handler.update(id, existing);
        return mapper.toResponse(updated);
    }

    public void delete(UUID id) {
        Interaccion existing = handler.get(id);
        validator.validateForDelete(existing);

        handler.delete(id);
    }
}
