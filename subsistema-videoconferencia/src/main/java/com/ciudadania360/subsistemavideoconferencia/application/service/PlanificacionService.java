package com.ciudadania360.subsistemavideoconferencia.application.service;

import com.ciudadania360.subsistemavideoconferencia.application.dto.planificacion.PlanificacionRequest;
import com.ciudadania360.subsistemavideoconferencia.application.dto.planificacion.PlanificacionResponse;
import com.ciudadania360.subsistemavideoconferencia.application.mapper.PlanificacionMapper;
import com.ciudadania360.subsistemavideoconferencia.domain.entity.Planificacion;
import com.ciudadania360.subsistemavideoconferencia.domain.handler.PlanificacionHandler;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PlanificacionService {

    private final PlanificacionHandler handler;
    private final PlanificacionMapper mapper;

    public PlanificacionService(PlanificacionHandler handler, PlanificacionMapper mapper) {
        this.handler = handler;
        this.mapper = mapper;
    }

    public List<PlanificacionResponse> list() {
        return handler.list().stream()
                .map(mapper::toResponse)
                .toList();
    }

    public PlanificacionResponse get(UUID id) {
        return mapper.toResponse(handler.get(id));
    }

    public PlanificacionResponse create(PlanificacionRequest request) {
        Planificacion entity = mapper.toEntity(request);
        entity.setId(UUID.randomUUID());
        return mapper.toResponse(handler.create(entity));
    }

    public PlanificacionResponse update(UUID id, PlanificacionRequest request) {
        Planificacion existing = handler.get(id);
        mapper.updateEntity(existing, request);
        return mapper.toResponse(handler.update(id, existing));
    }

    public void delete(UUID id) {
        handler.delete(id);
    }
}
