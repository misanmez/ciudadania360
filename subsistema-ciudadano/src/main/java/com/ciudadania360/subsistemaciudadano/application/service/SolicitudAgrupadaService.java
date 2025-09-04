package com.ciudadania360.subsistemaciudadano.application.service;

import com.ciudadania360.subsistemaciudadano.application.dto.solicitudagrupada.SolicitudAgrupadaRequest;
import com.ciudadania360.subsistemaciudadano.application.dto.solicitudagrupada.SolicitudAgrupadaResponse;
import com.ciudadania360.subsistemaciudadano.application.mapper.SolicitudAgrupadaMapper;
import com.ciudadania360.subsistemaciudadano.application.validator.SolicitudAgrupadaValidator;
import com.ciudadania360.subsistemaciudadano.domain.entity.SolicitudAgrupada;
import com.ciudadania360.subsistemaciudadano.domain.handler.SolicitudAgrupadaHandler;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SolicitudAgrupadaService {

    private final SolicitudAgrupadaHandler handler;
    private final SolicitudAgrupadaMapper mapper;
    private final SolicitudAgrupadaValidator validator;

    public SolicitudAgrupadaService(SolicitudAgrupadaHandler handler,
                                    SolicitudAgrupadaMapper mapper,
                                    SolicitudAgrupadaValidator validator) {
        this.handler = handler;
        this.mapper = mapper;
        this.validator = validator;
    }

    public List<SolicitudAgrupadaResponse> list() {
        return handler.list().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    public SolicitudAgrupadaResponse get(UUID id) {
        SolicitudAgrupada entity = handler.get(id);
        return mapper.toResponse(entity);
    }

    public SolicitudAgrupadaResponse create(SolicitudAgrupadaRequest request) {
        validator.validateCreate(request);

        SolicitudAgrupada entity = mapper.toEntity(request);
        entity.setId(UUID.randomUUID());

        SolicitudAgrupada created = handler.create(entity);
        return mapper.toResponse(created);
    }

    public SolicitudAgrupadaResponse update(UUID id, SolicitudAgrupadaRequest request) {
        validator.validateUpdate(request);

        SolicitudAgrupada existing = handler.get(id);
        mapper.updateEntity(existing, request); // MapStruct actualiza solo los campos no nulos

        SolicitudAgrupada updated = handler.update(id, existing);
        return mapper.toResponse(updated);
    }

    public void delete(UUID id) {
        SolicitudAgrupada entity = handler.get(id);
        validator.validateDelete(entity);
        handler.delete(id);
    }
}
