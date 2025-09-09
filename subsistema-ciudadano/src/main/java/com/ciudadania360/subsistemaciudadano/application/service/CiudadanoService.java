package com.ciudadania360.subsistemaciudadano.application.service;

import com.ciudadania360.shared.application.dto.ciudadano.CiudadanoRequest;
import com.ciudadania360.shared.application.dto.ciudadano.CiudadanoResponse;
import com.ciudadania360.subsistemaciudadano.application.mapper.CiudadanoMapper;
import com.ciudadania360.subsistemaciudadano.application.validator.CiudadanoValidator;
import com.ciudadania360.subsistemaciudadano.domain.entity.Ciudadano;
import com.ciudadania360.subsistemaciudadano.domain.handler.CiudadanoHandler;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CiudadanoService {

    private final CiudadanoHandler handler;
    private final CiudadanoMapper mapper;
    private final CiudadanoValidator validator;

    public CiudadanoService(CiudadanoHandler handler,
                            CiudadanoMapper mapper,
                            CiudadanoValidator validator) {
        this.handler = handler;
        this.mapper = mapper;
        this.validator = validator;
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
        validator.validateForCreate(request);

        Ciudadano entity = mapper.toEntity(request);
        entity.setId(UUID.randomUUID());
        entity.setVersion(0L);

        Ciudadano created = handler.    create(entity);
        return mapper.toResponse(created);
    }

    public CiudadanoResponse update(UUID id, CiudadanoRequest request) {
        validator.validateForUpdate(request);

        Ciudadano existing = handler.get(id);
        mapper.updateEntity(existing, request);

        Ciudadano updated = handler.update(id, existing);
        return mapper.toResponse(updated);
    }

    public void delete(UUID id) {
        handler.delete(id);
    }
}
