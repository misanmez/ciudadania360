package com.ciudadania360.gestionrolespermisos.application.service;

import com.ciudadania360.gestionrolespermisos.application.dto.permiso.PermisoRequest;
import com.ciudadania360.gestionrolespermisos.application.dto.permiso.PermisoResponse;
import com.ciudadania360.gestionrolespermisos.application.mapper.PermisoMapper;
import com.ciudadania360.gestionrolespermisos.domain.entity.Permiso;
import com.ciudadania360.gestionrolespermisos.domain.handler.PermisoHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PermisoService {

    private final PermisoHandler handler;
    private final PermisoMapper mapper;

    public List<PermisoResponse> list() {
        return handler.list().stream()
                .map(mapper::toResponse)
                .toList();
    }

    public PermisoResponse get(UUID id) {
        return mapper.toResponse(handler.get(id));
    }

    public PermisoResponse create(PermisoRequest request) {
        // MapStruct mapea los campos del request
        Permiso entity = mapper.toEntity(request);

        // Asignamos id y version
        entity.setId(UUID.randomUUID());
        entity.setVersion(0L);

        Permiso created = handler.create(entity);
        return mapper.toResponse(created);
    }

    public PermisoResponse update(UUID id, PermisoRequest request) {
        Permiso existing = handler.get(id);

        // MapStruct actualiza solo los campos no nulos
        mapper.updateEntity(existing, request);

        Permiso updated = handler.update(id, existing);
        return mapper.toResponse(updated);
    }

    public void delete(UUID id) {
        handler.delete(id);
    }
}
