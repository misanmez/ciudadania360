package com.ciudadania360.gestionrolespermisos.application.service;

import com.ciudadania360.gestionrolespermisos.application.dto.rol.RolRequest;
import com.ciudadania360.gestionrolespermisos.application.dto.rol.RolResponse;
import com.ciudadania360.gestionrolespermisos.application.mapper.RolMapper;
import com.ciudadania360.gestionrolespermisos.domain.entity.Rol;
import com.ciudadania360.gestionrolespermisos.domain.handler.RolHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RolService {

    private final RolHandler handler;
    private final RolMapper mapper;

    public List<RolResponse> list() {
        return handler.list().stream()
                .map(mapper::toResponse)
                .toList();
    }

    public RolResponse get(UUID id) {
        return mapper.toResponse(handler.get(id));
    }

    public RolResponse create(RolRequest request) {
        // MapStruct solo mapea los campos del request
        Rol entity = mapper.toEntity(request);

        // Asignamos id y version de forma consistente
        entity.setId(UUID.randomUUID());
        entity.setVersion(0L);

        Rol created = handler.create(entity);
        return mapper.toResponse(created);
    }

    public RolResponse update(UUID id, RolRequest request) {
        Rol existing = handler.get(id);

        // MapStruct actualiza solo los campos no nulos
        mapper.updateEntity(existing, request);

        Rol updated = handler.update(id, existing);
        return mapper.toResponse(updated);
    }

    public void delete(UUID id) {
        handler.delete(id);
    }
}
