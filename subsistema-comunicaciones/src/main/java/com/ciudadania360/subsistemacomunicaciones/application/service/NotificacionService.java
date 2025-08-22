package com.ciudadania360.subsistemacomunicaciones.application.service;

import com.ciudadania360.subsistemacomunicaciones.application.dto.notificacion.NotificacionRequest;
import com.ciudadania360.subsistemacomunicaciones.application.dto.notificacion.NotificacionResponse;
import com.ciudadania360.subsistemacomunicaciones.application.mapper.NotificacionMapper;
import com.ciudadania360.subsistemacomunicaciones.domain.entity.Notificacion;
import com.ciudadania360.subsistemacomunicaciones.domain.handler.NotificacionHandler;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class NotificacionService {

    private final NotificacionHandler handler;
    private final NotificacionMapper mapper;

    public NotificacionService(NotificacionHandler handler, NotificacionMapper mapper) {
        this.handler = handler;
        this.mapper = mapper;
    }

    public List<NotificacionResponse> list() {
        return handler.list().stream()
                .map(mapper::toResponse)
                .toList();
    }

    public NotificacionResponse get(UUID id) {
        return mapper.toResponse(handler.get(id));
    }

    public NotificacionResponse create(NotificacionRequest request) {
        // Convertimos el DTO en entidad
        Notificacion entity = mapper.toEntity(request);

        // Generamos ID y versión inicial
        entity.setId(UUID.randomUUID());
        entity.setVersion(0L);

        Notificacion created = handler.create(entity);
        return mapper.toResponse(created);
    }

    public NotificacionResponse update(UUID id, NotificacionRequest request) {
        Notificacion existing = handler.get(id);

        // MapStruct actualiza solo los campos no nulos
        mapper.updateEntity(existing, request);

        Notificacion updated = handler.update(id, existing);
        return mapper.toResponse(updated);
    }

    public void delete(UUID id) {
        handler.delete(id);
    }
}
