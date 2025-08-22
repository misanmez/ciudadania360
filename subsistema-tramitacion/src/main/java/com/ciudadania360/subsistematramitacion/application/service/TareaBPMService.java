package com.ciudadania360.subsistematramitacion.application.service;

import com.ciudadania360.subsistematramitacion.domain.entity.TareaBPM;
import com.ciudadania360.subsistematramitacion.domain.handler.TareaBPMHandler;
import com.ciudadania360.subsistematramitacion.application.dto.tareabpm.TareaBPMRequest;
import com.ciudadania360.subsistematramitacion.application.dto.tareabpm.TareaBPMResponse;
import com.ciudadania360.subsistematramitacion.application.mapper.TareaBPMMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TareaBPMService {

    private final TareaBPMHandler handler;
    private final TareaBPMMapper mapper;

    public TareaBPMService(TareaBPMHandler handler, TareaBPMMapper mapper) {
        this.handler = handler;
        this.mapper = mapper;
    }

    public List<TareaBPMResponse> list() {
        return handler.list().stream()
                .map(mapper::toResponse)
                .toList();
    }

    public TareaBPMResponse get(UUID id) {
        return mapper.toResponse(handler.get(id));
    }

    public TareaBPMResponse create(TareaBPMRequest request) {
        TareaBPM entity = mapper.toEntity(request);
        entity.setId(UUID.randomUUID());
        entity.setVersion(0L);
        return mapper.toResponse(handler.create(entity));
    }

    public TareaBPMResponse update(UUID id, TareaBPMRequest request) {
        TareaBPM existing = handler.get(id);
        mapper.updateEntity(existing, request);
        return mapper.toResponse(handler.update(id, existing));
    }

    public void delete(UUID id) {
        handler.delete(id);
    }
}
