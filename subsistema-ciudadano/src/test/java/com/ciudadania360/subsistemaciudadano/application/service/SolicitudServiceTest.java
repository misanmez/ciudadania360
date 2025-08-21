package com.ciudadania360.subsistemaciudadano.application.service;

import com.ciudadania360.subsistemaciudadano.application.dto.SolicitudRequest;
import com.ciudadania360.subsistemaciudadano.application.dto.SolicitudResponse;
import com.ciudadania360.subsistemaciudadano.application.mapper.SolicitudMapper;
import com.ciudadania360.subsistemaciudadano.domain.entity.Solicitud;
import com.ciudadania360.subsistemaciudadano.domain.handler.SolicitudHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SolicitudServiceTest {

    @Mock
    private SolicitudHandler handler;

    @Mock
    private SolicitudMapper mapper;

    @InjectMocks
    private SolicitudService svc;

    private Solicitud buildSolicitud() {
        UUID id = UUID.fromString("00000000-0000-0000-0000-000000000001");
        return Solicitud.builder()
                .id(id)
                .titulo("Título de prueba")
                .descripcion("Descripción de prueba")
                .tipo("TipoA")
                .canalEntrada("Web")
                .estado("Abierta")
                .prioridad("Alta")
                .numeroExpediente("EXP123")
                .fechaRegistro(Instant.parse("2025-01-01T10:00:00Z"))
                .fechaLimiteSLA(Instant.parse("2025-01-01T11:00:00Z"))
                .fechaCierre(null)
                .scoreRelevancia(BigDecimal.valueOf(0.9))
                .origen("Interno")
                .adjuntosCount(0)
                .encuestaEnviada(false)
                .referenciaExterna("REF001")
                .metadata("{}")
                .version(1L)
                .build();
    }

    private SolicitudRequest toRequest(Solicitud s) {
        SolicitudRequest request = new SolicitudRequest();
        request.setTitulo(s.getTitulo());
        request.setDescripcion(s.getDescripcion());
        request.setTipo(s.getTipo());
        request.setCanalEntrada(s.getCanalEntrada());
        request.setEstado(s.getEstado());
        request.setPrioridad(s.getPrioridad());
        request.setNumeroExpediente(s.getNumeroExpediente());
        request.setFechaLimiteSLA(s.getFechaLimiteSLA());
        request.setOrigen(s.getOrigen());
        request.setReferenciaExterna(s.getReferenciaExterna());
        request.setMetadata(s.getMetadata());
        return request;
    }

    private SolicitudResponse toResponse(Solicitud s) {
        return new SolicitudResponse(
                s.getId(),
                null, // ciudadanoId
                s.getTitulo(),
                s.getDescripcion(),
                s.getTipo(),
                s.getCanalEntrada(),
                s.getEstado(),
                s.getPrioridad(),
                null, // clasificacionId
                null, // ubicacionId
                s.getNumeroExpediente(),
                s.getFechaRegistro(),
                s.getFechaLimiteSLA(),
                s.getFechaCierre(),
                s.getScoreRelevancia(),
                s.getOrigen(),
                s.getAdjuntosCount(),
                s.getEncuestaEnviada(),
                s.getReferenciaExterna(),
                s.getMetadata(),
                s.getVersion()
        );
    }

    @Test
    void getAllDelegatesToHandler() {
        Solicitud s = buildSolicitud();
        SolicitudResponse expectedResponse = toResponse(s);

        when(handler.list()).thenReturn(List.of(s));
        when(mapper.toResponse(s)).thenReturn(expectedResponse);

        List<SolicitudResponse> result = svc.list();

        assertThat(result).containsExactly(expectedResponse);
        verify(handler).list();
        verify(mapper).toResponse(s);
    }

    @Test
    void getByIdDelegatesToHandler() {
        Solicitud s = buildSolicitud();
        SolicitudResponse expectedResponse = toResponse(s);

        when(handler.get(s.getId())).thenReturn(s);
        when(mapper.toResponse(s)).thenReturn(expectedResponse);

        SolicitudResponse result = svc.get(s.getId());

        assertThat(result).isEqualTo(expectedResponse);
        verify(handler).get(s.getId());
        verify(mapper).toResponse(s);
    }

    @Test
    void createDelegatesToHandler() {
        Solicitud s = buildSolicitud();
        SolicitudRequest request = toRequest(s);
        SolicitudResponse expectedResponse = toResponse(s);

        when(mapper.toEntity(request)).thenReturn(s);
        when(handler.create(s)).thenReturn(s);
        when(mapper.toResponse(s)).thenReturn(expectedResponse);

        SolicitudResponse result = svc.create(request);

        assertThat(result).isEqualTo(expectedResponse);
        verify(mapper).toEntity(request);
        verify(handler).create(s);
        verify(mapper).toResponse(s);
    }

    @Test
    void updateDelegatesToHandler() {
        Solicitud s = buildSolicitud();
        SolicitudRequest request = toRequest(s);
        SolicitudResponse expectedResponse = toResponse(s);

        when(handler.get(s.getId())).thenReturn(s);
        doNothing().when(mapper).updateEntity(s, request);
        when(handler.update(s.getId(), s)).thenReturn(s);
        when(mapper.toResponse(s)).thenReturn(expectedResponse);

        SolicitudResponse result = svc.update(s.getId(), request);

        assertThat(result).isEqualTo(expectedResponse);
        verify(handler).get(s.getId());
        verify(mapper).updateEntity(s, request);
        verify(handler).update(s.getId(), s);
        verify(mapper).toResponse(s);
    }

    @Test
    void deleteDelegatesToHandler() {
        UUID id = UUID.fromString("00000000-0000-0000-0000-000000000002");

        doNothing().when(handler).delete(id);
        svc.delete(id);

        verify(handler).delete(id);
        verifyNoInteractions(mapper);
    }
}
