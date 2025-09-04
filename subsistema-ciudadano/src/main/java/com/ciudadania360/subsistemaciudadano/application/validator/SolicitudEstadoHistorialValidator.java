package com.ciudadania360.subsistemaciudadano.application.validator;

import com.ciudadania360.subsistemaciudadano.application.dto.solicitudestadohistorial.SolicitudEstadoHistorialRequest;
import com.ciudadania360.subsistemaciudadano.domain.entity.SolicitudEstadoHistorial;
import org.springframework.stereotype.Component;
import com.ciudadania360.shared.exception.BadRequestException;

@Component
public class SolicitudEstadoHistorialValidator {

    public void validateCreate(SolicitudEstadoHistorialRequest entity) {
        checkRequiredFields(entity);
    }

    public void validateUpdate(SolicitudEstadoHistorialRequest entity) {
        checkRequiredFields(entity);
    }

    public void validateDelete(SolicitudEstadoHistorial entity) {
        if (entity == null) {
            throw new BadRequestException("Historial de estado no encontrado");
        }
    }

    private void checkRequiredFields(SolicitudEstadoHistorialRequest entity) {
        if (entity.getSolicitudId() == null) {
            throw new BadRequestException("Solicitud asociada es obligatoria");
        }
        if (entity.getEstadoAnterior() == null || entity.getEstadoAnterior().isBlank()) {
            throw new BadRequestException("Estado anterior es obligatorio");
        }
        if (entity.getEstadoNuevo() == null || entity.getEstadoNuevo().isBlank()) {
            throw new BadRequestException("Estado nuevo es obligatorio");
        }
        if (entity.getFechaCambio() == null) {
            throw new BadRequestException("Fecha de cambio es obligatoria");
        }
    }
}
