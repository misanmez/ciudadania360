package com.ciudadania360.subsistemaciudadano.application.service;

import com.ciudadania360.subsistemaciudadano.application.dto.solicitud.SolicitudRequest;
import com.ciudadania360.subsistemaciudadano.application.dto.solicitud.SolicitudResponse;
import com.ciudadania360.subsistemaciudadano.application.dto.solicitud.SolicitudSearchFilter;
import com.ciudadania360.subsistemaciudadano.application.mapper.SolicitudMapper;
import com.ciudadania360.subsistemaciudadano.application.validator.SolicitudValidator;
import com.ciudadania360.subsistemaciudadano.domain.entity.Solicitud;
import com.ciudadania360.subsistemaciudadano.domain.handler.SolicitudHandler;
import com.ciudadania360.shared.domain.entity.Empleado;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SolicitudService {

    private final SolicitudHandler handler;
    private final SolicitudMapper mapper;
    private final SolicitudValidator validator;

    public SolicitudService(SolicitudHandler handler, SolicitudMapper mapper, SolicitudValidator validator) {
        this.handler = handler;
        this.mapper = mapper;
        this.validator = validator;
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
        validator.validateCreate(request);

        Solicitud entity = mapper.toEntity(request);
        entity.setId(UUID.randomUUID());
        entity.setVersion(0L);

        Solicitud created = handler.create(entity);
        return mapper.toResponse(created);
    }

    public SolicitudResponse update(UUID id, SolicitudRequest request) {
        validator.validateUpdate(request);

        Solicitud existing = handler.get(id);
        mapper.updateEntity(existing, request);

        Solicitud updated = handler.update(id, existing);
        return mapper.toResponse(updated);
    }

    public void delete(UUID id) {
        Solicitud solicitud = handler.get(id);
        validator.validateDelete(solicitud);
        handler.delete(id);
    }

    public SolicitudResponse transition(UUID id, String nuevoEstado) {
        Solicitud solicitud = handler.get(id);
        validator.validateTransition(solicitud, nuevoEstado);

        solicitud.setEstado(nuevoEstado);
        solicitud.setFechaCierre("CERRADA".equalsIgnoreCase(nuevoEstado) ? Instant.now() : solicitud.getFechaCierre());

        return mapper.toResponse(handler.update(id, solicitud));
    }

    public SolicitudResponse recalculateSla(UUID id) {
        Solicitud solicitud = handler.get(id);
        Instant fechaRegistro = solicitud.getFechaRegistro() != null ? solicitud.getFechaRegistro() : Instant.now();
        solicitud.setFechaLimiteSLA(fechaRegistro.plusSeconds(172800)); // SLA +48h

        return mapper.toResponse(handler.update(id, solicitud));
    }

    public SolicitudResponse assign(UUID id, UUID agenteId) {
        Solicitud solicitud = handler.get(id);
        validator.validateAssign(solicitud, agenteId);

        // Crear un objeto Empleado con el ID recibido
        Empleado agente = new Empleado();
        agente.setId(agenteId);

        solicitud.setAgenteAsignado(agente);
        return mapper.toResponse(handler.update(id, solicitud));
    }


    public List<SolicitudResponse> search(SolicitudSearchFilter filter) {
        return handler.search(filter).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    public SolicitudResponse classifyAuto(UUID id) {
        Solicitud solicitud = handler.get(id);
        validator.validateClassifyAuto(solicitud);

        if (solicitud.getClasificacion() == null) {
            solicitud.setClasificacion(handler.getDefaultClasificacion());
        }
        return mapper.toResponse(handler.update(id, solicitud));
    }
}
