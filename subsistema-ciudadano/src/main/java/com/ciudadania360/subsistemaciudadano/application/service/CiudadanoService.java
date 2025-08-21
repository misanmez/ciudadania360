package com.ciudadania360.subsistemaciudadano.application.service;

import com.ciudadania360.subsistemaciudadano.application.dto.ciudadano.CiudadanoRequest;
import com.ciudadania360.subsistemaciudadano.application.dto.ciudadano.CiudadanoResponse;
import com.ciudadania360.subsistemaciudadano.application.mapper.CiudadanoMapper;
import com.ciudadania360.subsistemaciudadano.domain.entity.Ciudadano;
import com.ciudadania360.subsistemaciudadano.domain.handler.CiudadanoHandler;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CiudadanoService {

    private final CiudadanoHandler handler;
    private final CiudadanoMapper mapper;

    public CiudadanoService(CiudadanoHandler handler, CiudadanoMapper mapper) {
        this.handler = handler;
        this.mapper = mapper;
    }

    public List<CiudadanoResponse> list() {
        return handler.list().stream()
                .map(mapper::toResponse)
                .toList();
    }

    public CiudadanoResponse get(UUID id) {
        return mapper.toResponse(handler.get(id));
    }

    public CiudadanoResponse create(CiudadanoRequest request) {
        // MapStruct solo mapea los campos del request
        Ciudadano entity = mapper.toEntity(request);

        // Asignamos id y version aquí, de forma consistente
        entity.setId(UUID.randomUUID());
        entity.setVersion(0L);

        Ciudadano created = handler.create(entity);
        return mapper.toResponse(created);
    }

    public CiudadanoResponse update(UUID id, CiudadanoRequest request) {
        Ciudadano existing = handler.get(id);

        // MapStruct actualiza solo los campos no nulos
        mapper.updateEntity(existing, request);

        Ciudadano updated = handler.update(id, existing);
        return mapper.toResponse(updated);
    }

    public void delete(UUID id) {
        handler.delete(id);
    }
}
