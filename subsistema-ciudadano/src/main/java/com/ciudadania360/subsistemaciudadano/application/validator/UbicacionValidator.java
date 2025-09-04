package com.ciudadania360.subsistemaciudadano.application.validator;

import com.ciudadania360.subsistemaciudadano.application.dto.ubicacion.UbicacionRequest;
import com.ciudadania360.shared.exception.BadRequestException;
import org.springframework.stereotype.Component;

@Component
public class UbicacionValidator {

    public void validateCreate(UbicacionRequest request) {
        if (request.getDireccion() == null || request.getDireccion().isBlank()) {
            throw new BadRequestException("La dirección de la ubicación es obligatorio");
        }
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

    public void validateUpdate(UbicacionRequest request) {
        // Validaciones específicas de actualización, si es necesario
        validateCreate(request); // Por ejemplo, reutilizamos las mismas reglas
    }

    public void validateExistence(Object entity) {
        if (entity == null) {
            throw new BadRequestException("Entidad no encontrada");
        }
    }
}
