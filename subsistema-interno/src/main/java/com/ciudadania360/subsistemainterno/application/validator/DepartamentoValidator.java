package com.ciudadania360.subsistemainterno.application.validator;

import com.ciudadania360.shared.exception.BadRequestException;
import com.ciudadania360.shared.exception.BusinessException;
import com.ciudadania360.subsistemainterno.application.dto.departamento.DepartamentoRequest;
import org.springframework.stereotype.Component;

@Component
public class DepartamentoValidator {

    public void validateForCreate(DepartamentoRequest request) {
        checkRequiredFields(request);
        checkBusinessRules(request, true);
    }

    public void validateForUpdate(DepartamentoRequest request) {
        checkBusinessRules(request, false);
    }

    private void checkRequiredFields(DepartamentoRequest r) {
        if (r.getNombre() == null || r.getNombre().isBlank())
            throw new BadRequestException("Nombre del departamento obligatorio");
    }

    private void checkBusinessRules(DepartamentoRequest r, boolean isCreate) {
        if (isCreate && isDepartamentoDuplicado(r.getNombre()))
            throw new BusinessException("Ya existe un departamento con ese nombre");
    }

    private boolean isDepartamentoDuplicado(String nombre) {
        // TODO: implementar verificación real en base de datos
        return false;
    }
}
