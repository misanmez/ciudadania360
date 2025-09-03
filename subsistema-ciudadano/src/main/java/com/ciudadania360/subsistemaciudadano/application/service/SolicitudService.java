package com.ciudadania360.subsistemaciudadano.application.service;

import com.ciudadania360.subsistemaciudadano.application.dto.solicitud.SolicitudRequest;
import com.ciudadania360.subsistemaciudadano.application.dto.solicitud.SolicitudResponse;
import com.ciudadania360.subsistemaciudadano.application.dto.solicitud.SolicitudSearchFilter;
import com.ciudadania360.subsistemaciudadano.application.mapper.SolicitudMapper;
import com.ciudadania360.subsistemaciudadano.domain.entity.Solicitud;
import com.ciudadania360.subsistemaciudadano.domain.handler.SolicitudHandler;
import com.ciudadania360.shared.exception.BadRequestException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SolicitudService {

    private final SolicitudHandler handler;
    private final SolicitudMapper mapper;

    public SolicitudService(SolicitudHandler handler, SolicitudMapper mapper) {
        this.handler = handler;
        this.mapper = mapper;
    }

    public List<SolicitudResponse> list() {
        return handler.list().stream()
                .map(mapper::toResponse)
                .toList();
    }

    public SolicitudResponse get(UUID id) {
        return mapper.toResponse(handler.get(id));
    }

    public SolicitudResponse create(SolicitudRequest request) {
        validateRequest(request);

        Solicitud entity = mapper.toEntity(request);
        entity.setId(UUID.randomUUID());
        entity.setVersion(0L);

        Solicitud created = handler.create(entity);
        return mapper.toResponse(created);
    }

    public SolicitudResponse update(UUID id, SolicitudRequest request) {
        validateRequest(request);

        Solicitud existing = handler.get(id);
        mapper.updateEntity(existing, request);

        Solicitud updated = handler.update(id, existing);
        return mapper.toResponse(updated);
    }

    public void delete(UUID id) {
        handler.delete(id);
    }

    public SolicitudResponse transition(UUID id, String nuevoEstado) {
        Solicitud solicitud = handler.get(id);
        solicitud.setEstado(nuevoEstado);
        solicitud.setFechaCierre("CERRADA".equalsIgnoreCase(nuevoEstado) ? Instant.now() : solicitud.getFechaCierre());
        return mapper.toResponse(handler.update(id, solicitud));
    }

    public SolicitudResponse recalculateSla(UUID id) {
        Solicitud solicitud = handler.get(id);
        // lógica simple de ejemplo: SLA = registro + 48h
        solicitud.setFechaLimiteSLA(solicitud.getFechaRegistro().plusSeconds(172800));
        return mapper.toResponse(handler.update(id, solicitud));
    }

    public SolicitudResponse assign(UUID id, String agenteId) {
        Solicitud solicitud = handler.get(id);
        solicitud.setAgenteAsignado(agenteId);
        return mapper.toResponse(handler.update(id, solicitud));
    }

    public List<SolicitudResponse> search(SolicitudSearchFilter filter) {
        return handler.search(filter).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    public SolicitudResponse classifyAuto(UUID id) {
        Solicitud solicitud = handler.get(id);
        // ejemplo: asignar clasificación "GENERICA"
        if (solicitud.getClasificacion() == null) {
            solicitud.setClasificacion(handler.getDefaultClasificacion());
        }
        return mapper.toResponse(handler.update(id, solicitud));
    }

    private void validateRequest(SolicitudRequest request) {
        if (request.getTitulo() == null || request.getTitulo().isBlank()) {
            throw new BadRequestException("El título de la solicitud es obligatorio");
        }

        if (request.getEstado() == null || request.getEstado().isBlank()) {
            throw new BadRequestException("El estado de la solicitud es obligatorio");
        }

        if (request.getPrioridad() != null &&
                !request.getPrioridad().matches("ALTA|MEDIA|BAJA")) {
            throw new BadRequestException("Prioridad inválida, debe ser ALTA, MEDIA o BAJA");
        }
    }
}
