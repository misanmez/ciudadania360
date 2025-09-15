package com.ciudadania360.aywebfwk.ws.tramitacion.mapper;

import com.ciudadania360.shared.application.dto.flujo.FlujoRequest;
import com.ciudadania360.shared.application.dto.flujo.FlujoResponse;
import org.springframework.stereotype.Component;

@Component
public class FlujoMapper {

    // Shared -> Subsistema
    public com.ciudadania360.subsistematramitacion.application.dto.flujo.FlujoRequest toSubsistemaRequest(FlujoRequest request) {
        if (request == null) return null;
        return com.ciudadania360.subsistematramitacion.application.dto.flujo.FlujoRequest.builder()
                .nombre(request.getNombre())
                .descripcion(request.getDescripcion())
                .activo(request.getActivo())
                .tipo(request.getTipo())
                .slaHoras(request.getSlaHoras())
                .pasosDefinition(request.getPasosDefinition())
                .build();
    }

    public com.ciudadania360.subsistematramitacion.application.dto.flujo.FlujoResponse toSubsistemaResponse(FlujoResponse response) {
        if (response == null) return null;
        return com.ciudadania360.subsistematramitacion.application.dto.flujo.FlujoResponse.builder()
                .id(response.getId())
                .nombre(response.getNombre())
                .descripcion(response.getDescripcion())
                .activo(response.getActivo())
                .tipo(response.getTipo())
                .slaHoras(response.getSlaHoras())
                .pasosDefinition(response.getPasosDefinition())
                .build();
    }

    // Subsistema -> Shared
    public FlujoRequest toSharedRequest(com.ciudadania360.subsistematramitacion.application.dto.flujo.FlujoRequest request) {
        if (request == null) return null;
        return FlujoRequest.builder()
                .nombre(request.getNombre())
                .descripcion(request.getDescripcion())
                .activo(request.getActivo())
                .tipo(request.getTipo())
                .slaHoras(request.getSlaHoras())
                .pasosDefinition(request.getPasosDefinition())
                .build();
    }

    public FlujoResponse toSharedResponse(com.ciudadania360.subsistematramitacion.application.dto.flujo.FlujoResponse response) {
        if (response == null) return null;
        return FlujoResponse.builder()
                .id(response.getId())
                .nombre(response.getNombre())
                .descripcion(response.getDescripcion())
                .activo(response.getActivo())
                .tipo(response.getTipo())
                .slaHoras(response.getSlaHoras())
                .pasosDefinition(response.getPasosDefinition())
                .build();
    }
}
