package com.ciudadania360.subsistemainterno.application.service;

import com.ciudadania360.shared.application.dto.empleado.EmpleadoRequest;
import com.ciudadania360.shared.application.dto.empleado.EmpleadoResponse;
import com.ciudadania360.subsistemainterno.application.mapper.EmpleadoMapper;
import com.ciudadania360.subsistemainterno.application.validator.EmpleadoValidator;
import com.ciudadania360.shared.domain.entity.Empleado;
import com.ciudadania360.subsistemainterno.domain.handler.EmpleadoHandler;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EmpleadoService {

    private final EmpleadoHandler handler;
    private final EmpleadoMapper mapper;
    private final EmpleadoValidator validator;

    public EmpleadoService(EmpleadoHandler handler,
                           EmpleadoMapper mapper,
                           EmpleadoValidator validator) {
        this.handler = handler;
        this.mapper = mapper;
        this.validator = validator;
    }

    public List<EmpleadoResponse> list() {
        return handler.list().stream()
                .map(mapper::toResponse)
                .toList();
    }

    public EmpleadoResponse get(UUID id) {
        return mapper.toResponse(handler.get(id));
    }

    public EmpleadoResponse create(EmpleadoRequest request) {
        validator.validateForCreate(request);

        Empleado entity = mapper.toEntity(request);
        entity.setId(UUID.randomUUID());
        entity.setVersion(0L);

        Empleado created = handler.create(entity);
        return mapper.toResponse(created);
    }

    public EmpleadoResponse update(UUID id, EmpleadoRequest request) {
        validator.validateForUpdate(request);

        Empleado existing = handler.get(id);
        mapper.updateEntity(existing, request);

        Empleado updated = handler.update(id, existing);
        return mapper.toResponse(updated);
    }

    public void delete(UUID id) {
        handler.delete(id);
    }
}
