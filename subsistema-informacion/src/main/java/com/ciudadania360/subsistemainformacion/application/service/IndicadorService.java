package com.ciudadania360.subsistemainformacion.application.service;

import com.ciudadania360.subsistemainformacion.domain.entity.Indicador;
import com.ciudadania360.subsistemainformacion.domain.handler.IndicadorHandler;
import com.ciudadania360.subsistemainformacion.application.mapper.IndicadorMapper;
import com.ciudadania360.subsistemainformacion.application.dto.indicador.IndicadorRequest;
import com.ciudadania360.subsistemainformacion.application.dto.indicador.IndicadorResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class IndicadorService {

    private final IndicadorHandler handler;
    private final IndicadorMapper mapper;

    public IndicadorService(IndicadorHandler handler, IndicadorMapper mapper) {
        this.handler = handler;
        this.mapper = mapper;
    }

    public List<IndicadorResponse> list() {
        return handler.list().stream().map(mapper::toResponse).toList();
    }

    public IndicadorResponse get(UUID id) {
        return mapper.toResponse(handler.get(id));
    }

    public IndicadorResponse create(IndicadorRequest request) {
        Indicador entity = mapper.toEntity(request);
        entity.setId(UUID.randomUUID());
        entity.setVersion(0L);
        return mapper.toResponse(handler.create(entity));
    }

    public IndicadorResponse update(UUID id, IndicadorRequest request) {
        Indicador existing = handler.get(id);
        mapper.updateEntity(existing, request);
        return mapper.toResponse(handler.update(id, existing));
    }

    public void delete(UUID id) {
        handler.delete(id);
    }
}
