package com.ciudadania360.subsistemaciudadano.application.service;

import com.ciudadania360.subsistemaciudadano.application.dto.notificacion.NotificacionRequest;
import com.ciudadania360.subsistemaciudadano.application.dto.notificacion.NotificacionResponse;
import com.ciudadania360.subsistemaciudadano.application.mapper.NotificacionMapper;
import com.ciudadania360.subsistemaciudadano.application.validator.NotificacionValidator;
import com.ciudadania360.subsistemaciudadano.domain.entity.Notificacion;
import com.ciudadania360.subsistemaciudadano.domain.handler.NotificacionHandler;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class NotificacionService {

    private final NotificacionHandler handler;
    private final NotificacionMapper mapper;
    private final NotificacionValidator validator;

    public NotificacionService(NotificacionHandler handler, NotificacionMapper mapper, NotificacionValidator validator) {
        this.handler = handler;
        this.mapper = mapper;
        this.validator = validator;
    }

    public List<NotificacionResponse> list() {
        return handler.list().stream()
                .map(mapper::toResponse)
                .toList();
    }

    public NotificacionResponse get(UUID id) {
        Notificacion e = handler.get(id);
        return mapper.toResponse(e);
    }

    public NotificacionResponse create(NotificacionRequest request) {
        validator.validateCreate(request);

        Notificacion entity = mapper.toEntity(request);
        entity.setId(UUID.randomUUID());
        entity.setVersion(0L);

        Notificacion created = handler.create(entity);
        return mapper.toResponse(created);
    }

    public NotificacionResponse update(UUID id, NotificacionRequest request) {
        validator.validateUpdate(request);

        Notificacion existing = handler.get(id);
        mapper.updateEntity(existing, request);

        Notificacion updated = handler.update(id, existing);
        return mapper.toResponse(updated);
    }

    public void delete(UUID id) {
        boolean exists = handler.exists(id);
        validator.validateDelete(id, exists);
        handler.delete(id);
    }
}
