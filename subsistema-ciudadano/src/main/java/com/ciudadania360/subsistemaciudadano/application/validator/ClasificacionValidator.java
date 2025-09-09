package com.ciudadania360.subsistemaciudadano.application.validator;

import com.ciudadania360.shared.application.dto.clasificacion.ClasificacionRequest;
import com.ciudadania360.subsistemaciudadano.domain.repository.ClasificacionRepository;
import com.ciudadania360.shared.exception.BadRequestException;
import com.ciudadania360.shared.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ClasificacionValidator {

    private final ClasificacionRepository repository;

    public void validateCreate(ClasificacionRequest request) {
        checkRequiredFields(request);
        checkFieldLengths(request);
        checkDuplicados(request, null);
    }

    public void validateUpdate(UUID existingId, ClasificacionRequest request) {
        checkFieldLengths(request);
        checkDuplicados(request, existingId);
    }

    private void checkRequiredFields(ClasificacionRequest request) {
        if (request.getCodigo() == null || request.getCodigo().isBlank()) {
            throw new BadRequestException("El código de la clasificación es obligatorio");
        }
        if (request.getNombre() == null || request.getNombre().isBlank()) {
            throw new BadRequestException("El nombre de la clasificación es obligatorio");
        }
    }

    private void checkFieldLengths(ClasificacionRequest request) {
        if (request.getCodigo() != null && request.getCodigo().length() > 50) {
            throw new BadRequestException("El código no puede superar 50 caracteres");
        }
        if (request.getNombre() != null && request.getNombre().length() > 120) {
            throw new BadRequestException("El nombre no puede superar 120 caracteres");
        }
    }

    private void checkDuplicados(ClasificacionRequest request, UUID existingId) {
        Optional<?> byCodigo = repository.findByCodigo(request.getCodigo());
        if (byCodigo.isPresent() && !byCodigo.get().equals(existingId)) {
            throw new BusinessException("Ya existe una clasificación con ese código");
        }

        Optional<?> byNombre = repository.findByNombreIgnoreCase(request.getNombre());
        if (byNombre.isPresent() && !byNombre.get().equals(existingId)) {
            throw new BusinessException("Ya existe una clasificación con ese nombre");
        }
    }
}
