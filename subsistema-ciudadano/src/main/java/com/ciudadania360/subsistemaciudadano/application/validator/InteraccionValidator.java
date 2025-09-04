package com.ciudadania360.subsistemaciudadano.application.validator;

import com.ciudadania360.subsistemaciudadano.application.dto.interaccion.InteraccionRequest;
import com.ciudadania360.subsistemaciudadano.domain.entity.Interaccion;
import com.ciudadania360.subsistemaciudadano.domain.handler.CiudadanoHandler;
import com.ciudadania360.subsistemaciudadano.domain.handler.SolicitudHandler;
import com.ciudadania360.shared.exception.BadRequestException;
import com.ciudadania360.shared.exception.BusinessException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class InteraccionValidator {

    private final CiudadanoHandler ciudadanoHandler;
    private final SolicitudHandler solicitudHandler;

    private static final List<String> CANALES_VALIDOS = Arrays.asList("WEB", "APP", "PRESENCIAL", "TELEFONO", "REDES_SOCIALES");
    private static final List<String> VISIBILIDAD_VALIDOS = Arrays.asList("PUBLICA", "PRIVADA", "INTERNA", "RESTRINGIDA");

    public InteraccionValidator(CiudadanoHandler ciudadanoHandler, SolicitudHandler solicitudHandler) {
        this.ciudadanoHandler = ciudadanoHandler;
        this.solicitudHandler = solicitudHandler;
    }

    // Validación para creación
    public void validateForCreate(InteraccionRequest request) {
        if (request.getSolicitudId() == null)
            throw new BadRequestException("Solicitud asociada obligatoria");

        if (!solicitudHandler.exists(request.getSolicitudId()))
            throw new BusinessException("La solicitud indicada no existe");

        if (request.getCiudadanoId() == null)
            throw new BadRequestException("Ciudadano asociado obligatorio");

        if (!ciudadanoHandler.exists(request.getCiudadanoId()))
            throw new BusinessException("El ciudadano indicado no existe");

        if (request.getCanal() != null && !CANALES_VALIDOS.contains(request.getCanal().toUpperCase()))
            throw new BadRequestException("Canal inválido. Valores válidos: " + CANALES_VALIDOS);

        if (request.getVisibilidad() != null && !VISIBILIDAD_VALIDOS.contains(request.getVisibilidad().toUpperCase()))
            throw new BadRequestException("Visibilidad inválida. Valores válidos: " + VISIBILIDAD_VALIDOS);

        if (request.getMensaje() == null || request.getMensaje().isBlank())
            throw new BadRequestException("El mensaje de la interacción es obligatorio");

        if (request.getMensaje() != null && request.getMensaje().length() > 2000)
            throw new BadRequestException("El mensaje no puede superar 2000 caracteres");
    }

    // Validación para actualización
    public void validateForUpdate(InteraccionRequest request, Interaccion existing) {
        if (existing == null)
            throw new BadRequestException("Interacción no encontrada");

        if (request.getCanal() != null && !CANALES_VALIDOS.contains(request.getCanal().toUpperCase()))
            throw new BadRequestException("Canal inválido. Valores válidos: " + CANALES_VALIDOS);

        if (request.getVisibilidad() != null && !VISIBILIDAD_VALIDOS.contains(request.getVisibilidad().toUpperCase()))
            throw new BadRequestException("Visibilidad inválida. Valores válidos: " + VISIBILIDAD_VALIDOS);

        if (request.getMensaje() != null && request.getMensaje().length() > 2000)
            throw new BadRequestException("El mensaje no puede superar 2000 caracteres");
    }

    // Validación para eliminación
    public void validateForDelete(Interaccion existing) {
        if (existing == null)
            throw new BadRequestException("Interacción no encontrada");

        if ("PUBLICA".equalsIgnoreCase(existing.getVisibilidad()))
            throw new BusinessException("No se puede eliminar una interacción pública");
    }
}
