package com.ciudadania360.subsistemaciudadano.application.validator;

import com.ciudadania360.subsistemaciudadano.application.dto.encuesta.EncuestaRequest;
import com.ciudadania360.subsistemaciudadano.domain.handler.SolicitudHandler;
import com.ciudadania360.shared.exception.BadRequestException;
import com.ciudadania360.shared.exception.BusinessException;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.UUID;

@Component
public class EncuestaValidator {

    private final SolicitudHandler solicitudHandler;
    private final ObjectMapper objectMapper;

    public EncuestaValidator(SolicitudHandler solicitudHandler, ObjectMapper objectMapper) {
        this.solicitudHandler = solicitudHandler;
        this.objectMapper = objectMapper;
    }

    public void validateCreate(EncuestaRequest request) {
        checkRequiredFields(request);
        checkFormats(request);
        checkBusinessRules(request);
    }

    public void validateUpdate(EncuestaRequest request) {
        checkFormats(request);
        checkBusinessRules(request);
    }

    public void validateDelete(UUID encuestaId, boolean exists) {
        if (!exists) {
            throw new BadRequestException("Encuesta no encontrada");
        }
    }

    // --- Métodos privados ---

    private void checkRequiredFields(EncuestaRequest r) {
        if (r.getSolicitudId() == null) {
            throw new BadRequestException("Solicitud asociada obligatoria");
        }
        if (!solicitudHandler.exists(r.getSolicitudId())) {
            throw new BusinessException("La solicitud indicada no existe");
        }
        if (r.getTipo() == null || r.getTipo().isBlank()) {
            throw new BadRequestException("Tipo de encuesta obligatorio");
        }
    }

    private void checkFormats(EncuestaRequest r) {
        if (r.getEstado() != null &&
                !r.getEstado().matches("ENVIADA|PENDIENTE|COMPLETADA")) {
            throw new BadRequestException("Estado inválido. Valores válidos: ENVIADA, PENDIENTE, COMPLETADA");
        }

        if (r.getFechaEnvio() != null && r.getFechaRespuesta() != null &&
                r.getFechaRespuesta().isBefore(r.getFechaEnvio())) {
            throw new BadRequestException("La fecha de respuesta no puede ser anterior a la fecha de envío");
        }

        if (r.getRespuestas() != null) {
            try {
                objectMapper.readTree(r.getRespuestas());
            } catch (Exception ex) {
                throw new BadRequestException("Respuestas debe ser un JSON válido");
            }
        }

        if (r.getMetadata() != null) {
            try {
                objectMapper.readTree(r.getMetadata());
            } catch (Exception ex) {
                throw new BadRequestException("Metadata debe ser un JSON válido");
            }
        }
    }

    private void checkBusinessRules(EncuestaRequest r) {
        // Aquí se podrían añadir reglas de negocio adicionales
    }
}
