package com.ciudadania360.subsistemaciudadano.application.service;

import com.ciudadania360.shared.application.dto.clasificacion.ClasificacionRequest;
import com.ciudadania360.shared.application.dto.clasificacion.ClasificacionResponse;
import com.ciudadania360.subsistemaciudadano.application.mapper.ClasificacionMapper;
import com.ciudadania360.subsistemaciudadano.domain.entity.Clasificacion;
import com.ciudadania360.subsistemaciudadano.domain.handler.ClasificacionHandler;
import com.ciudadania360.subsistemaciudadano.application.validator.ClasificacionValidator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClasificacionService {

    private final ClasificacionHandler handler;
    private final ClasificacionMapper mapper;
    private final ClasificacionValidator validator;

    public List<ClasificacionResponse> list() {
        return handler.list().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    public ClasificacionResponse get(UUID id) {
        return mapper.toResponse(handler.get(id));
    }

    public ClasificacionResponse create(ClasificacionRequest request) {
        // --- Validación de negocio ---
        validator.validateCreate(request);

        // MapStruct mapea los campos del request
        Clasificacion entity = mapper.toEntity(request);
        entity.setId(UUID.randomUUID());
        entity.setVersion(0L);

        Clasificacion created = handler.create(entity);
        return mapper.toResponse(created);
    }

    public ClasificacionResponse update(UUID id, ClasificacionRequest request) {
        Clasificacion existing = handler.get(id);

        // --- Validación de negocio ---
        validator.validateUpdate(id, request);

        // MapStruct actualiza solo los campos no nulos
        mapper.updateEntity(existing, request);

        Clasificacion updated = handler.update(id, existing);
        return mapper.toResponse(updated);
    }

    public void delete(UUID id) {
        handler.delete(id);
    }
}
