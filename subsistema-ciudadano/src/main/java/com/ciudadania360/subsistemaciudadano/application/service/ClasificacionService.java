package com.ciudadania360.subsistemaciudadano.application.service;

import com.ciudadania360.subsistemaciudadano.application.dto.clasificacion.ClasificacionRequest;
import com.ciudadania360.subsistemaciudadano.application.dto.clasificacion.ClasificacionResponse;
import com.ciudadania360.subsistemaciudadano.application.mapper.ClasificacionMapper;
import com.ciudadania360.subsistemaciudadano.domain.entity.Clasificacion;
import com.ciudadania360.subsistemaciudadano.domain.handler.ClasificacionHandler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClasificacionService {

    private final ClasificacionHandler handler;
    private final ClasificacionMapper clasificacionMapper;

    public List<ClasificacionResponse> list() {
        return handler.list().stream()
                .map(clasificacionMapper::toResponse)
                .collect(Collectors.toList());
    }

    public ClasificacionResponse get(UUID id) {
        return clasificacionMapper.toResponse(handler.get(id));
    }

    public ClasificacionResponse create(ClasificacionRequest request) {
        // MapStruct solo mapea los campos del request
        Clasificacion entity = clasificacionMapper.toEntity(request);

        // Asignamos id y version aquí
        entity.setId(UUID.randomUUID());
        entity.setVersion(0L);

        return clasificacionMapper.toResponse(handler.create(entity));
    }

    public ClasificacionResponse update(UUID id, ClasificacionRequest request) {
        Clasificacion existing = handler.get(id);

        // Actualizamos solo los campos no nulos
        clasificacionMapper.updateEntity(existing, request);

        return clasificacionMapper.toResponse(handler.update(id, existing));
    }

    public void delete(UUID id) {
        handler.delete(id);
    }
}
