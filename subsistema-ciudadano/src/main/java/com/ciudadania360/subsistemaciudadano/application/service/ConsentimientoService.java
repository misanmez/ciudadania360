package com.ciudadania360.subsistemaciudadano.application.service;

import com.ciudadania360.subsistemaciudadano.application.dto.consentimiento.ConsentimientoRequest;
import com.ciudadania360.subsistemaciudadano.application.dto.consentimiento.ConsentimientoResponse;
import com.ciudadania360.subsistemaciudadano.application.mapper.ConsentimientoMapper;
import com.ciudadania360.subsistemaciudadano.domain.entity.Consentimiento;
import com.ciudadania360.subsistemaciudadano.domain.handler.ConsentimientoHandler;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ConsentimientoService {

    private final ConsentimientoHandler handler;
    private final ConsentimientoMapper mapper;

    public ConsentimientoService(ConsentimientoHandler handler, ConsentimientoMapper mapper) {

        this.handler = handler;
        this.mapper = mapper;
    }

    public List<ConsentimientoResponse> list() {
        return handler.list().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    public ConsentimientoResponse get(UUID id) {
        return mapper.toResponse(handler.get(id));
    }

    public ConsentimientoResponse create(ConsentimientoRequest request) {
        Consentimiento entity = mapper.toEntity(request);

        // Asignamos ID aquí
        entity.setId(UUID.randomUUID());

        return mapper.toResponse(handler.create(entity));
    }

    public ConsentimientoResponse update(UUID id, ConsentimientoRequest request) {
        Consentimiento existing = handler.get(id);

        // Actualizamos solo los campos no nulos del request
        existing.setTipo(request.getTipo() != null ? request.getTipo() : existing.getTipo());
        existing.setOtorgado(request.getOtorgado() != null ? request.getOtorgado() : existing.getOtorgado());
        existing.setCiudadanoId(request.getCiudadanoId() != null ? request.getCiudadanoId() : existing.getCiudadanoId());

        Consentimiento updated = handler.update(id, existing);
        return mapper.toResponse(updated);
    }

    public void delete(UUID id) {
        handler.delete(id);
    }
}
