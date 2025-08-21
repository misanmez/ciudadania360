package com.ciudadania360.gestionrolespermisos.application.service;

import com.ciudadania360.gestionrolespermisos.application.dto.usuariorol.UsuarioRolRequest;
import com.ciudadania360.gestionrolespermisos.application.dto.usuariorol.UsuarioRolResponse;
import com.ciudadania360.gestionrolespermisos.application.mapper.UsuarioRolMapper;
import com.ciudadania360.gestionrolespermisos.domain.entity.UsuarioRol;
import com.ciudadania360.gestionrolespermisos.domain.handler.UsuarioRolHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsuarioRolService {

    private final UsuarioRolHandler handler;
    private final UsuarioRolMapper mapper;

    public List<UsuarioRolResponse> list() {
        return handler.list().stream()
                .map(mapper::toResponse)
                .toList();
    }

    public UsuarioRolResponse get(UUID id) {
        return mapper.toResponse(handler.get(id));
    }

    public UsuarioRolResponse create(UsuarioRolRequest request) {
        UsuarioRol entity = mapper.toEntity(request);

        // Asignamos id y version si aplica
        entity.setId(UUID.randomUUID());
        entity.setVersion(0L);

        UsuarioRol created = handler.create(entity);
        return mapper.toResponse(created);
    }

    public UsuarioRolResponse update(UUID id, UsuarioRolRequest request) {
        UsuarioRol existing = handler.get(id);

        // MapStruct actualiza solo los campos no nulos
        mapper.updateEntity(existing, request);

        UsuarioRol updated = handler.update(id, existing);
        return mapper.toResponse(updated);
    }

    public void delete(UUID id) {
        handler.delete(id);
    }
}
