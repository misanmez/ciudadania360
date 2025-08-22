package com.ciudadania360.subsistemainformacion.application.service;

import com.ciudadania360.subsistemainformacion.domain.entity.Dataset;
import com.ciudadania360.subsistemainformacion.domain.handler.DatasetHandler;
import com.ciudadania360.subsistemainformacion.application.mapper.DatasetMapper;
import com.ciudadania360.subsistemainformacion.application.dto.dataset.DatasetRequest;
import com.ciudadania360.subsistemainformacion.application.dto.dataset.DatasetResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DatasetService {

    private final DatasetHandler handler;
    private final DatasetMapper mapper;

    public DatasetService(DatasetHandler handler, DatasetMapper mapper) {
        this.handler = handler;
        this.mapper = mapper;
    }

    public List<DatasetResponse> list() {
        return handler.list().stream()
                .map(mapper::toResponse)
                .toList();
    }

    public DatasetResponse get(UUID id) {
        return mapper.toResponse(handler.get(id));
    }

    public DatasetResponse create(DatasetRequest request) {
        Dataset entity = mapper.toEntity(request);
        entity.setId(UUID.randomUUID());
        entity.setVersion(0L);
        return mapper.toResponse(handler.create(entity));
    }

    public DatasetResponse update(UUID id, DatasetRequest request) {
        Dataset existing = handler.get(id);
        mapper.updateEntity(existing, request);
        return mapper.toResponse(handler.update(id, existing));
    }

    public void delete(UUID id) {
        handler.delete(id);
    }
}
