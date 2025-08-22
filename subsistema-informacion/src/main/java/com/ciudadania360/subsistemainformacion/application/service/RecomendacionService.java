package com.ciudadania360.subsistemainformacion.application.service;

import com.ciudadania360.subsistemainformacion.domain.entity.Recomendacion;
import com.ciudadania360.subsistemainformacion.domain.handler.RecomendacionHandler;
import com.ciudadania360.subsistemainformacion.application.mapper.RecomendacionMapper;
import com.ciudadania360.subsistemainformacion.application.dto.recomendacion.RecomendacionRequest;
import com.ciudadania360.subsistemainformacion.application.dto.recomendacion.RecomendacionResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RecomendacionService {

    private final RecomendacionHandler handler;
    private final RecomendacionMapper mapper;

    public RecomendacionService(RecomendacionHandler handler, RecomendacionMapper mapper) {
        this.handler = handler;
        this.mapper = mapper;
    }

    public List<RecomendacionResponse> list() {
        return handler.list().stream().map(mapper::toResponse).toList();
    }

    public RecomendacionResponse get(UUID id) {
        return mapper.toResponse(handler.get(id));
    }

    public RecomendacionResponse create(RecomendacionRequest request) {
        Recomendacion entity = mapper.toEntity(request);
        entity.setId(UUID.randomUUID());
        return mapper.toResponse(handler.create(entity));
    }

    public RecomendacionResponse update(UUID id, RecomendacionRequest request) {
        Recomendacion existing = handler.get(id);
        mapper.updateEntity(existing, request);
        return mapper.toResponse(handler.update(id, existing));
    }

    public void delete(UUID id) {
        handler.delete(id);
    }
}
