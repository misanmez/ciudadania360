package com.ciudadania360.subsistemaciudadano.application.validator;

import com.ciudadania360.shared.exception.BadRequestException;
import com.ciudadania360.shared.exception.BusinessException;
import com.ciudadania360.subsistemaciudadano.application.dto.reglaclasificacion.ReglaClasificacionRequest;
import com.ciudadania360.subsistemaciudadano.domain.entity.ReglaClasificacion;
import com.ciudadania360.subsistemaciudadano.domain.handler.ReglaClasificacionHandler;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ReglaClasificacionValidator {

    private final ReglaClasificacionHandler handler;

    public ReglaClasificacionValidator(ReglaClasificacionHandler handler) {
        this.handler = handler;
    }

    public void validateCreate(ReglaClasificacionRequest request) {
        if (request.getNombre() == null || request.getNombre().isBlank()) {
            throw new BadRequestException("El nombre de la regla de clasificación es obligatorio");
        }
        if (request.getExpresion() == null || request.getExpresion().isBlank()) {
            throw new BadRequestException("La expresión de la regla de clasificación es obligatoria");
        }

        if (handler.existsByNombre(request.getNombre())) {
            throw new BusinessException("Ya existe una regla de clasificación con ese nombre");
        }
    }

    public void validateUpdate(UUID id, ReglaClasificacionRequest request) {
        if (request.getNombre() != null && !request.getNombre().isBlank()) {
            if (handler.existsByNombreExcludingId(request.getNombre(), id)) {
                throw new BusinessException("Ya existe una regla de clasificación con ese nombre");
            }
        }
    }

    public void validateForDelete(ReglaClasificacion regla) {
        if (regla == null) {
            throw new BadRequestException("Regla de clasificación no encontrada");
        }
        // Aquí se podrían añadir reglas de negocio antes de borrar, si fuera necesario
    }
}
