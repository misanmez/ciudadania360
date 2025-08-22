package com.ciudadania360.subsistemainformacion.application.service;

import com.ciudadania360.subsistemainformacion.domain.entity.Duda;
import com.ciudadania360.subsistemainformacion.domain.handler.DudaHandler;
import com.ciudadania360.subsistemainformacion.application.mapper.DudaMapper;
import com.ciudadania360.subsistemainformacion.application.dto.duda.DudaRequest;
import com.ciudadania360.subsistemainformacion.application.dto.duda.DudaResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DudaService {

    private final DudaHandler handler;
    private final DudaMapper mapper;

    public DudaService(DudaHandler handler, DudaMapper mapper) {
        this.handler = handler;
        this.mapper = mapper;
    }

    public List<DudaResponse> list() {
        return handler.list().stream().map(mapper::toResponse).toList();
    }

    public DudaResponse get(UUID id) {
        return mapper.toResponse(handler.get(id));
    }

    public DudaResponse create(DudaRequest request) {
        Duda entity = mapper.toEntity(request);
        entity.setId(UUID.randomUUID());
        return mapper.toResponse(handler.create(entity));
    }

    public DudaResponse update(UUID id, DudaRequest request) {
        Duda existing = handler.get(id);
        mapper.updateEntity(existing, request);
        return mapper.toResponse(handler.update(id, existing));
    }

    public void delete(UUID id) {
        handler.delete(id);
    }
}
