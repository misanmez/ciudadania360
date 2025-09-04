package com.ciudadania360.subsistemaciudadano.application.service;

import com.ciudadania360.subsistemaciudadano.application.dto.consentimiento.ConsentimientoRequest;
import com.ciudadania360.subsistemaciudadano.application.dto.consentimiento.ConsentimientoResponse;
import com.ciudadania360.subsistemaciudadano.application.mapper.ConsentimientoMapper;
import com.ciudadania360.subsistemaciudadano.application.validator.ConsentimientoValidator;
import com.ciudadania360.subsistemaciudadano.domain.entity.Consentimiento;
import com.ciudadania360.subsistemaciudadano.domain.handler.ConsentimientoHandler;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ConsentimientoService {

    private final ConsentimientoHandler handler;
    private final ConsentimientoMapper mapper;
    private final ConsentimientoValidator validator;

    public ConsentimientoService(ConsentimientoHandler handler,
                                 ConsentimientoMapper mapper,
                                 ConsentimientoValidator validator) {
        this.handler = handler;
        this.mapper = mapper;
        this.validator = validator;
    }

    public List<ConsentimientoResponse> list() {
        return handler.list().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    public ConsentimientoResponse get(UUID id) {
        return mapper.toResponse(handler.get(id));
    }

    public ConsentimientoResponse create(ConsentimientoRequest request) {
        validator.validate(request, true);

        Consentimiento entity = mapper.toEntity(request);
        entity.setId(UUID.randomUUID());

        Consentimiento created = handler.create(entity);
        return mapper.toResponse(created);
    }

    public ConsentimientoResponse update(UUID id, ConsentimientoRequest request) {
        Consentimiento existing = handler.get(id);
        validator.validate(request, false);

        mapper.updateEntity(existing, request);

        Consentimiento updated = handler.update(id, existing);
        return mapper.toResponse(updated);
    }

    public void delete(UUID id) {
        handler.delete(id);
    }
}
