package com.ciudadania360.subsistemaciudadano.application.service;

import com.ciudadania360.subsistemaciudadano.application.dto.ubicacion.UbicacionRequest;
import com.ciudadania360.subsistemaciudadano.application.dto.ubicacion.UbicacionResponse;
import com.ciudadania360.subsistemaciudadano.application.mapper.UbicacionMapper;
import com.ciudadania360.subsistemaciudadano.application.validator.UbicacionValidator;
import com.ciudadania360.subsistemaciudadano.domain.entity.Ubicacion;
import com.ciudadania360.subsistemaciudadano.domain.handler.UbicacionHandler;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UbicacionService {

    private final UbicacionHandler handler;
    private final UbicacionMapper mapper;
    private final UbicacionValidator validator;

    public UbicacionService(UbicacionHandler handler, UbicacionMapper mapper, UbicacionValidator validator) {
        this.handler = handler;
        this.mapper = mapper;
        this.validator = validator;
    }

    public List<UbicacionResponse> list() {
        return handler.list().stream()
                .map(mapper::toResponse)
                .toList();
    }

    public UbicacionResponse get(UUID id) {
        Ubicacion existing = handler.get(id);
        validator.validateExistence(existing);
        return mapper.toResponse(existing);
    }

    public UbicacionResponse create(UbicacionRequest request) {
        validator.validateCreate(request);
        Ubicacion entity = mapper.toEntity(request);
        Ubicacion created = handler.create(entity);
        return mapper.toResponse(created);
    }

    public UbicacionResponse update(UUID id, UbicacionRequest request) {
        Ubicacion existing = handler.get(id);
        validator.validateExistence(existing);
        validator.validateUpdate(request);
        mapper.updateEntity(existing, request); // MapStruct actualiza solo los campos no nulos
        Ubicacion updated = handler.update(id, existing);
        return mapper.toResponse(updated);
    }

    public void delete(UUID id) {
        Ubicacion existing = handler.get(id);
        validator.validateExistence(existing);
        handler.delete(id);
    }
}
