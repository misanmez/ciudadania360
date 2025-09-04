package com.ciudadania360.subsistemaciudadano.application.validator;

import com.ciudadania360.subsistemaciudadano.application.dto.notificacion.NotificacionRequest;
import com.ciudadania360.subsistemaciudadano.domain.handler.SolicitudHandler;
import com.ciudadania360.shared.exception.BadRequestException;
import com.ciudadania360.shared.exception.BusinessException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class NotificacionValidator {

    private final SolicitudHandler solicitudHandler;
    private final ObjectMapper objectMapper;

    public NotificacionValidator(SolicitudHandler solicitudHandler, ObjectMapper objectMapper) {
        this.solicitudHandler = solicitudHandler;
        this.objectMapper = objectMapper;
    }

    public void validateCreate(NotificacionRequest request) {
        checkRequiredFields(request);
        checkFormats(request);
        checkBusinessRules(request);
    }

    public void validateUpdate(NotificacionRequest request) {
        checkFormats(request);
        checkBusinessRules(request);
    }

    public void validateDelete(UUID id, boolean exists) {
        if (!exists) {
            throw new BadRequestException("Notificación no encontrada");
        }
    }

    // --- Privados ---
    private void checkRequiredFields(NotificacionRequest r) {
        if (r.getSolicitudId() == null) {
            throw new BadRequestException("Solicitud asociada obligatoria");
        }
        if (!solicitudHandler.exists(r.getSolicitudId())) {
            throw new BusinessException("La solicitud indicada no existe");
        }
        if (r.getCanal() == null || r.getCanal().isBlank()) {
            throw new BadRequestException("Canal obligatorio (SMS, Email, App)");
        }
        if (r.getEstado() == null || r.getEstado().isBlank()) {
            throw new BadRequestException("Estado obligatorio (enviado, pendiente, error)");
        }
    }

    private void checkFormats(NotificacionRequest r) {
        if (r.getEstado() != null &&
                !r.getEstado().matches("enviado|pendiente|error")) {
            throw new BadRequestException("Estado inválido. Valores válidos: enviado, pendiente, error");
        }

        if (r.getMetadata() != null) {
            try {
                objectMapper.readTree(r.getMetadata());
            } catch (Exception ex) {
                throw new BadRequestException("Metadata debe ser un JSON válido");
            }
        }
    }

    private void checkBusinessRules(NotificacionRequest r) {
        // Reglas de negocio adicionales
    }
}
