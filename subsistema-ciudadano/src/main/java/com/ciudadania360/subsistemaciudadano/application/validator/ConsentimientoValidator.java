package com.ciudadania360.subsistemaciudadano.application.validator;

import com.ciudadania360.shared.exception.BadRequestException;
import com.ciudadania360.shared.exception.BusinessException;
import com.ciudadania360.subsistemaciudadano.application.dto.consentimiento.ConsentimientoRequest;
import com.ciudadania360.subsistemaciudadano.domain.handler.ConsentimientoHandler;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ConsentimientoValidator {

    private final ConsentimientoHandler handler;

    public ConsentimientoValidator(ConsentimientoHandler handler) {
        this.handler = handler;
    }

    public void validate(ConsentimientoRequest request, boolean isCreate) {

        // --- BadRequestException: campos obligatorios ---
        if (isCreate) {
            if (request.getCiudadanoId() == null) {
                throw new BadRequestException("El ID del ciudadano es obligatorio");
            }
            if (request.getTipo() == null || request.getTipo().isBlank()) {
                throw new BadRequestException("El tipo de consentimiento es obligatorio");
            }
            if (request.getOtorgado() == null) {
                throw new BadRequestException("El campo otorgado es obligatorio");
            }
        }

        // --- ValidationException: reglas complejas ---
        if (request.getTipo() != null && request.getTipo().length() > 100) {
            throw new ValidationException("El tipo de consentimiento no puede superar 100 caracteres");
        }

        // --- BusinessException: reglas de negocio ---
        if (isCreate && isConsentimientoDuplicado(request.getCiudadanoId(), request.getTipo())) {
            throw new BusinessException("Ya existe un consentimiento de ese tipo para este ciudadano");
        }
    }

    private boolean isConsentimientoDuplicado(UUID ciudadanoId, String tipo) {
        return handler.list().stream()
                .anyMatch(c -> c.getCiudadano().getId().equals(ciudadanoId) &&
                        c.getTipo().equalsIgnoreCase(tipo));
    }
}
