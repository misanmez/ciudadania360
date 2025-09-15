package com.ciudadania360.aywebfwk.ws.comunicaciones.mapper;

import com.ciudadania360.shared.application.dto.comunicacion.ComunicacionRequest;
import com.ciudadania360.shared.application.dto.comunicacion.ComunicacionResponse;
import org.springframework.stereotype.Component;

@Component
public class ComunicacionMapper {

    // Shared -> Subsistema
    public com.ciudadania360.subsistemacomunicaciones.application.dto.comunicacion.ComunicacionRequest toSubsistemaRequest(ComunicacionRequest request) {
        if (request == null) return null;
        return com.ciudadania360.subsistemacomunicaciones.application.dto.comunicacion.ComunicacionRequest.builder()
                .asunto(request.getAsunto())
                .cuerpo(request.getCuerpo())
                .canal(request.getCanal())
                .estado(request.getEstado())
                .destinatario(request.getDestinatario())
                .ciudadanoId(request.getCiudadanoId())
                .programadaPara(request.getProgramadaPara())
                .build();
    }

    public com.ciudadania360.subsistemacomunicaciones.application.dto.comunicacion.ComunicacionResponse toSubsistemaResponse(ComunicacionResponse response) {
        if (response == null) return null;
        return com.ciudadania360.subsistemacomunicaciones.application.dto.comunicacion.ComunicacionResponse.builder()
                .id(response.getId())
                .asunto(response.getAsunto())
                .cuerpo(response.getCuerpo())
                .canal(response.getCanal())
                .estado(response.getEstado())
                .destinatario(response.getDestinatario())
                .ciudadanoId(response.getCiudadanoId())
                .programadaPara(response.getProgramadaPara())
                .build();
    }

    // Subsistema -> Shared
    public ComunicacionRequest toSharedRequest(com.ciudadania360.subsistemacomunicaciones.application.dto.comunicacion.ComunicacionRequest request) {
        if (request == null) return null;
        return ComunicacionRequest.builder()
                .asunto(request.getAsunto())
                .cuerpo(request.getCuerpo())
                .canal(request.getCanal())
                .estado(request.getEstado())
                .destinatario(request.getDestinatario())
                .ciudadanoId(request.getCiudadanoId())
                .programadaPara(request.getProgramadaPara())
                .build();
    }

    public ComunicacionResponse toSharedResponse(com.ciudadania360.subsistemacomunicaciones.application.dto.comunicacion.ComunicacionResponse response) {
        if (response == null) return null;
        return ComunicacionResponse.builder()
                .id(response.getId())
                .asunto(response.getAsunto())
                .cuerpo(response.getCuerpo())
                .canal(response.getCanal())
                .estado(response.getEstado())
                .destinatario(response.getDestinatario())
                .ciudadanoId(response.getCiudadanoId())
                .programadaPara(response.getProgramadaPara())
                .build();
    }
}
