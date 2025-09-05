package com.ciudadania360.subsistemaciudadano.application.validator;

import com.ciudadania360.subsistemaciudadano.application.dto.solicitud.SolicitudRequest;
import com.ciudadania360.subsistemaciudadano.domain.entity.Solicitud;
import com.ciudadania360.subsistemaciudadano.domain.handler.CiudadanoHandler;
import com.ciudadania360.shared.exception.BadRequestException;
import com.ciudadania360.shared.exception.BusinessException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SolicitudValidator {

    private final CiudadanoHandler ciudadanoHandler;

    public SolicitudValidator(CiudadanoHandler ciudadanoHandler) {
        this.ciudadanoHandler = ciudadanoHandler;
    }

    public void validateCreate(SolicitudRequest request) {
        checkRequiredFields(request);
        checkFormats(request);
        checkBusinessRules(request);
    }

    public void validateUpdate(SolicitudRequest request) {
        // En actualización, reutilizamos validaciones básicas
        checkFormats(request);
        checkBusinessRules(request);
    }

    public void validateDelete(Solicitud solicitud) {
        if (solicitud == null)
            throw new BadRequestException("Solicitud no encontrada");

        if ("CERRADA".equalsIgnoreCase(solicitud.getEstado()))
            throw new BusinessException("No se puede eliminar una solicitud cerrada");
    }

    public void validateTransition(Solicitud solicitud, String nuevoEstado) {
        if (solicitud == null)
            throw new BadRequestException("Solicitud no encontrada");

        if (nuevoEstado == null || nuevoEstado.isBlank())
            throw new BadRequestException("Nuevo estado obligatorio");

        if ("CERRADA".equalsIgnoreCase(solicitud.getEstado()))
            throw new BusinessException("No se puede cambiar el estado de una solicitud ya cerrada");
    }

    public void validateAssign(Solicitud solicitud, UUID agenteId) {
        if (solicitud == null)
            throw new BadRequestException("Solicitud no encontrada");

        if (agenteId == null)
            throw new BadRequestException("Agente obligatorio para asignar la solicitud");

        if ("CERRADA".equalsIgnoreCase(solicitud.getEstado()))
            throw new BusinessException("No se puede asignar un agente a una solicitud cerrada");
    }

    public void validateClassifyAuto(Solicitud solicitud) {
        if (solicitud == null)
            throw new BadRequestException("Solicitud no encontrada");

        if ("CERRADA".equalsIgnoreCase(solicitud.getEstado()))
            throw new BusinessException("No se puede clasificar automáticamente una solicitud cerrada");
    }

    // --- Métodos privados de soporte ---

    private void checkRequiredFields(SolicitudRequest request) {
        if (request.getTitulo() == null || request.getTitulo().isBlank())
            throw new BadRequestException("Título obligatorio");

        if (request.getTipo() == null || request.getTipo().isBlank())
            throw new BadRequestException("Tipo de solicitud obligatorio");

        if (request.getCiudadanoId() == null)
            throw new BadRequestException("Ciudadano obligatorio");

        if (!ciudadanoHandler.exists(request.getCiudadanoId()))
            throw new BusinessException("El empleado indicado no existe");
    }

    private void checkFormats(SolicitudRequest request) {
        if (request.getPrioridad() != null && !request.getPrioridad().matches("ALTA|MEDIA|BAJA"))
            throw new BadRequestException("Prioridad inválida, debe ser ALTA, MEDIA o BAJA");

        if (request.getFechaRegistro() != null && request.getFechaLimiteSLA() != null &&
                request.getFechaLimiteSLA().isBefore(request.getFechaRegistro()))
            throw new BadRequestException("La fecha límite SLA no puede ser anterior a la fecha de registro");
    }

    private void checkBusinessRules(SolicitudRequest request) {
        // Aquí podrían añadirse más reglas de negocio específicas de creación/actualización
    }
}
