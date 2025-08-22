package com.ciudadania360.subsistemacomunicaciones.application.service;

import com.ciudadania360.subsistemacomunicaciones.application.dto.respuestaencuesta.RespuestaEncuestaRequest;
import com.ciudadania360.subsistemacomunicaciones.application.dto.respuestaencuesta.RespuestaEncuestaResponse;
import com.ciudadania360.subsistemacomunicaciones.application.mapper.RespuestaEncuestaMapper;
import com.ciudadania360.subsistemacomunicaciones.domain.entity.RespuestaEncuesta;
import com.ciudadania360.subsistemacomunicaciones.domain.handler.RespuestaEncuestaHandler;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RespuestaEncuestaService {

    private final RespuestaEncuestaHandler handler;
    private final RespuestaEncuestaMapper mapper;

    public RespuestaEncuestaService(RespuestaEncuestaHandler handler, RespuestaEncuestaMapper mapper) {
        this.handler = handler;
        this.mapper = mapper;
    }

    public List<RespuestaEncuestaResponse> list() {
        return handler.list().stream()
                .map(mapper::toResponse)
                .toList();
    }

    public RespuestaEncuestaResponse get(UUID id) {
        return mapper.toResponse(handler.get(id));
    }

    public RespuestaEncuestaResponse create(RespuestaEncuestaRequest request) {
        RespuestaEncuesta entity = mapper.toEntity(request);

        entity.setId(UUID.randomUUID());
        entity.setVersion(0L);

        RespuestaEncuesta created = handler.create(entity);
        return mapper.toResponse(created);
    }

    public RespuestaEncuestaResponse update(UUID id, RespuestaEncuestaRequest request) {
        RespuestaEncuesta existing = handler.get(id);

        mapper.updateEntity(existing, request);

        RespuestaEncuesta updated = handler.update(id, existing);
        return mapper.toResponse(updated);
    }

    public void delete(UUID id) {
        handler.delete(id);
    }
}
