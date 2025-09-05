package com.ciudadania360.subsistemainterno.application.validator;

import com.ciudadania360.shared.exception.BadRequestException;
import com.ciudadania360.shared.exception.BusinessException;
import com.ciudadania360.subsistemainterno.application.dto.partetrabajo.ParteTrabajoRequest;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class ParteTrabajoValidator {

    private static final String[] ESTADOS_VALIDOS = {"PENDIENTE", "EN_PROCESO", "FINALIZADO", "CANCELADO"};

    public void validateForCreate(ParteTrabajoRequest request) {
        checkRequiredFields(request);
        checkFormats(request);
        checkBusinessRules(request, true);
    }

    public void validateForUpdate(ParteTrabajoRequest request, Object existingEntity) {
        checkFormats(request);
        checkBusinessRules(request, false);
    }

    public void validateForDelete(Object existingEntity) {
        // Por ejemplo: no permitir borrar partes finalizados
        // if (existingEntity.getEstado().equals("FINALIZADO")) throw new BusinessException(...)
    }

    private void checkRequiredFields(ParteTrabajoRequest r) {
        if (r.getTitulo() == null || r.getTitulo().isBlank())
            throw new BadRequestException("Título obligatorio");

        if (r.getDescripcion() == null || r.getDescripcion().isBlank())
            throw new BadRequestException("Descripción obligatoria");

        if (r.getEstado() == null || r.getEstado().isBlank())
            throw new BadRequestException("Estado obligatorio");

        if (r.getEmpleadoAsignadoId() == null)
            throw new BadRequestException("Empleado obligatorio");
    }

    private void checkFormats(ParteTrabajoRequest r) {
        if (r.getEstado() != null) {
            boolean valido = false;
            for (String e : ESTADOS_VALIDOS) {
                if (e.equalsIgnoreCase(r.getEstado())) {
                    valido = true;
                    break;
                }
            }
            if (!valido) {
                throw new BadRequestException("Estado inválido. Valores válidos: PENDIENTE, EN_PROCESO, FINALIZADO, CANCELADO");
            }
        }

        if (r.getFechaFin() != null && r.getFechaInicio() != null && r.getFechaFin().before(r.getFechaInicio())) {
            throw new BadRequestException("La fecha fin no puede ser anterior a la fecha de inicio");
        }
    }

    private void checkBusinessRules(ParteTrabajoRequest r, boolean isCreate) {
        if (isCreate && isParteDuplicada(r.getEmpleadoAsignadoId(), r.getFechaInicio())) {
            throw new BusinessException("Ya existe un parte de trabajo para este empleado en la fecha indicada");
        }
    }

    private boolean isParteDuplicada(UUID empleadoId, Date fecha) {
        // TODO: implementación real contra la base de datos
        return false;
    }
}
