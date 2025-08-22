package com.ciudadania360.subsistemavideoconferencia.application.service;

import com.ciudadania360.subsistemavideoconferencia.application.dto.dispositivo.DispositivoRequest;
import com.ciudadania360.subsistemavideoconferencia.application.dto.dispositivo.DispositivoResponse;
import com.ciudadania360.subsistemavideoconferencia.application.mapper.DispositivoMapper;
import com.ciudadania360.subsistemavideoconferencia.domain.entity.Dispositivo;
import com.ciudadania360.subsistemavideoconferencia.domain.handler.DispositivoHandler;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DispositivoService {

    private final DispositivoHandler handler;
    private final DispositivoMapper mapper;

    public DispositivoService(DispositivoHandler handler, DispositivoMapper mapper) {
        this.handler = handler;
        this.mapper = mapper;
    }

    public List<DispositivoResponse> list() {
        return handler.list().stream()
                .map(mapper::toResponse)
                .toList();
    }

    public DispositivoResponse get(UUID id) {
        return mapper.toResponse(handler.get(id));
    }

    public DispositivoResponse create(DispositivoRequest request) {
        Dispositivo entity = mapper.toEntity(request);
        entity.setId(UUID.randomUUID());
        entity.setVersion(0L);
        return mapper.toResponse(handler.create(entity));
    }

    public DispositivoResponse update(UUID id, DispositivoRequest request) {
        Dispositivo existing = handler.get(id);
        mapper.updateEntity(existing, request);
        return mapper.toResponse(handler.update(id, existing));
    }

    public void delete(UUID id) {
        handler.delete(id);
    }
}
