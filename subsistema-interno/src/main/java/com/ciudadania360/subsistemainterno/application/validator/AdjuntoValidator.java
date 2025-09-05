package com.ciudadania360.subsistemainterno.application.validator;

import com.ciudadania360.shared.exception.BadRequestException;
import com.ciudadania360.shared.exception.BusinessException;
import com.ciudadania360.subsistemainterno.application.dto.adjunto.AdjuntoRequest;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AdjuntoValidator {

    public void validateForCreate(AdjuntoRequest request) {
        checkRequiredFields(request);
        checkBusinessRules(request, true);
    }

    public void validateForUpdate(AdjuntoRequest request) {
        checkBusinessRules(request, false);
    }

    private void checkRequiredFields(AdjuntoRequest r) {
        if (r.getNombreArchivo() == null || r.getNombreArchivo().isBlank())
            throw new BadRequestException("Nombre de archivo obligatorio");
        if (r.getTipo() == null || r.getTipo().isBlank())
            throw new BadRequestException("Tipo de archivo obligatorio");
        if (r.getParteTrabajoId() == null)
            throw new BadRequestException("ParteTrabajo asociado obligatorio");
    }

    private void checkBusinessRules(AdjuntoRequest r, boolean isCreate) {
        // Ejemplo: evitar duplicados por nombre en el mismo parte de trabajo
        if (isCreate && isAdjuntoDuplicado(r.getParteTrabajoId(), r.getNombreArchivo()))
            throw new BusinessException("Ya existe un adjunto con el mismo nombre en este parte de trabajo");
    }

    private boolean isAdjuntoDuplicado(UUID parteTrabajoId, String nombreArchivo) {
        // TODO: implementación real contra la base de datos
        return false;
    }
}
