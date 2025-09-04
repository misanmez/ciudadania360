package com.ciudadania360.subsistemaciudadano.application.validator;

import com.ciudadania360.subsistemaciudadano.application.dto.solicitudagrupada.SolicitudAgrupadaRequest;
import com.ciudadania360.subsistemaciudadano.domain.entity.SolicitudAgrupada;
import org.springframework.stereotype.Component;
import com.ciudadania360.shared.exception.BadRequestException;

@Component
public class SolicitudAgrupadaValidator {

    public void validateCreate(SolicitudAgrupadaRequest entity) {
        checkRequiredFields(entity);
    }

    public void validateUpdate(SolicitudAgrupadaRequest entity) {
        checkRequiredFields(entity);
    }

    public void validateDelete(SolicitudAgrupada entity) {
        if (entity == null) {
            throw new BadRequestException("Solicitud agrupada no encontrada");
        }
    }

    private void checkRequiredFields(SolicitudAgrupadaRequest entity) {
        if (entity.getSolicitudPadreId() == null) {
            throw new BadRequestException("Solicitud padre es obligatoria");
        }
        if (entity.getSolicitudHijaId() == null) {
            throw new BadRequestException("Solicitud hija es obligatoria");
        }
    }
}
