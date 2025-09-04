package com.ciudadania360.subsistemaciudadano.application.validator;

import com.ciudadania360.shared.exception.BadRequestException;
import com.ciudadania360.subsistemaciudadano.application.dto.direccion.DireccionRequest;
import com.ciudadania360.subsistemaciudadano.domain.entity.Direccion;
import com.ciudadania360.subsistemaciudadano.domain.handler.DireccionHandler;
import org.springframework.stereotype.Component;

@Component
public class DireccionValidator {

    private final DireccionHandler handler;

    public DireccionValidator(DireccionHandler handler) {
        this.handler = handler;
    }

    public void validate(DireccionRequest request, boolean isCreate) {

        // --- BadRequestException: campos obligatorios ---
        if (isCreate) {
            if (request.getCiudadanoId() == null) {
                throw new BadRequestException("El ID del ciudadano es obligatorio");
            }
            if (request.getVia() == null || request.getVia().isBlank()) {
                throw new BadRequestException("La vía es obligatoria");
            }
            if (request.getNumero() == null || request.getNumero().isBlank()) {
                throw new BadRequestException("El número es obligatorio");
            }
        }

        // --- BadRequestException: reglas de validación ---
        if (request.getCp() != null && !request.getCp().matches("\\d{5}")) {
            throw new BadRequestException("El código postal debe tener 5 dígitos");
        }

        if (request.getLat() != null && (request.getLat() < -90 || request.getLat() > 90)) {
            throw new BadRequestException("Latitud inválida");
        }

        if (request.getLon() != null && (request.getLon() < -180 || request.getLon() > 180)) {
            throw new BadRequestException("Longitud inválida");
        }
    }

    public void validateForDelete(Direccion direccion) {
        if (direccion == null) {
            throw new BadRequestException("Dirección no encontrada");
        }
        // Aquí podríamos agregar reglas de negocio adicionales antes de eliminar
    }
}
