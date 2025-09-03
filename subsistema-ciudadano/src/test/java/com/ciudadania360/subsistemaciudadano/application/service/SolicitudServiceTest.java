package com.ciudadania360.subsistemaciudadano.application.service;

import com.ciudadania360.subsistemaciudadano.application.dto.solicitud.SolicitudRequest;
import com.ciudadania360.subsistemaciudadano.application.dto.solicitud.SolicitudResponse;
import com.ciudadania360.subsistemaciudadano.application.mapper.SolicitudMapper;
import com.ciudadania360.subsistemaciudadano.domain.entity.Clasificacion;
import com.ciudadania360.subsistemaciudadano.domain.entity.Solicitud;
import com.ciudadania360.subsistemaciudadano.domain.handler.SolicitudHandler;
import com.ciudadania360.subsistemaciudadano.application.dto.solicitud.SolicitudSearchFilter;
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

    @Mock private SolicitudHandler handler;
    @Mock private SolicitudMapper mapper;
    @InjectMocks private SolicitudService svc;

    private Solicitud buildSolicitud() {
        UUID id = UUID.fromString("00000000-0000-0000-0000-000000000001");
        return Solicitud.builder()
                .id(id)
                .titulo("Título de prueba")
                .descripcion("Descripción de prueba")
                .tipo("TipoA")
                .canalEntrada("Web")
                .estado("Abierta")
                // ✅ Aseguramos que la prioridad sea válida
                .prioridad("ALTA")
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

    // --- TESTS ORIGINALES ---
    @Test void getAllDelegatesToHandler() {
        Solicitud s = buildSolicitud();
        SolicitudResponse expectedResponse = toResponse(s);

        when(handler.list()).thenReturn(List.of(s));
        when(mapper.toResponse(s)).thenReturn(expectedResponse);

        List<SolicitudResponse> result = svc.list();

        assertThat(result).containsExactly(expectedResponse);
        verify(handler).list();
        verify(mapper).toResponse(s);
    }

    @Test void getByIdDelegatesToHandler() {
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
        Solicitud e = buildSolicitud();
        SolicitudRequest request = toRequest(e);
        SolicitudResponse expectedResponse = toResponse(e);

        when(mapper.toEntity(request)).thenReturn(e);
        when(handler.create(e)).thenReturn(e);
        when(mapper.toResponse(e)).thenReturn(expectedResponse);

        SolicitudResponse result = svc.create(request);

        assertThat(result).isEqualTo(expectedResponse);
        verify(mapper).toEntity(request);
        verify(handler).create(e);
        verify(mapper).toResponse(e);
    }

    @Test
    void updateDelegatesToHandler() {
        Solicitud e = buildSolicitud();
        SolicitudRequest request = toRequest(e);

        // ⚡ Aseguramos prioridad válida
        request.setPrioridad("MEDIA");

        when(handler.get(e.getId())).thenReturn(e);
        doNothing().when(mapper).updateEntity(any(Solicitud.class), eq(request));
        when(handler.update(eq(e.getId()), any(Solicitud.class))).thenReturn(e);
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        SolicitudResponse result = svc.update(e.getId(), request);

        assertThat(result).isEqualTo(toResponse(e));
        verify(handler).get(e.getId());
        verify(mapper).updateEntity(same(e), eq(request));
        verify(handler).update(eq(e.getId()), same(e));
        verify(mapper).toResponse(e);
    }


    @Test void deleteDelegatesToHandler() {
        UUID id = UUID.fromString("00000000-0000-0000-0000-000000000002");
        doNothing().when(handler).delete(id);
        svc.delete(id);

        verify(handler).delete(id);
        verifyNoInteractions(mapper);
    }

    // --- NUEVOS TESTS ---
    @Test
    void transitionUpdatesEstadoAndFechaCierre() {
        Solicitud s = buildSolicitud();
        SolicitudResponse expectedResponse = toResponse(s);

        when(handler.get(s.getId())).thenReturn(s);
        when(handler.update(s.getId(), s)).thenReturn(s);
        when(mapper.toResponse(s)).thenReturn(expectedResponse);

        SolicitudResponse result = svc.transition(s.getId(), "CERRADA");

        assertThat(result).isEqualTo(expectedResponse);
        assertThat(s.getEstado()).isEqualTo("CERRADA");
        assertThat(s.getFechaCierre()).isNotNull();

        verify(handler).get(s.getId());
        verify(handler).update(s.getId(), s);
        verify(mapper).toResponse(s);
    }

    @Test
    void recalculateSlaUpdatesFechaLimiteSLA() {
        Solicitud s = buildSolicitud();
        SolicitudResponse expectedResponse = toResponse(s);

        when(handler.get(s.getId())).thenReturn(s);
        when(handler.update(s.getId(), s)).thenReturn(s);
        when(mapper.toResponse(s)).thenReturn(expectedResponse);

        SolicitudResponse result = svc.recalculateSla(s.getId());

        assertThat(result).isEqualTo(expectedResponse);
        assertThat(s.getFechaLimiteSLA()).isEqualTo(s.getFechaRegistro().plusSeconds(48 * 3600));

        verify(handler).get(s.getId());
        verify(handler).update(s.getId(), s);
        verify(mapper).toResponse(s);
    }

    @Test
    void assignUpdatesAgenteAsignado() {
        Solicitud s = buildSolicitud();
        SolicitudResponse expectedResponse = toResponse(s);
        String agenteId = "AGENTE123";

        when(handler.get(s.getId())).thenReturn(s);
        when(handler.update(s.getId(), s)).thenReturn(s);
        when(mapper.toResponse(s)).thenReturn(expectedResponse);

        SolicitudResponse result = svc.assign(s.getId(), agenteId);

        assertThat(result).isEqualTo(expectedResponse);
        assertThat(s.getAgenteAsignado()).isEqualTo(agenteId);

        verify(handler).get(s.getId());
        verify(handler).update(s.getId(), s);
        verify(mapper).toResponse(s);
    }

    @Test
    void searchDelegatesToHandler() {
        Solicitud s = buildSolicitud();
        SolicitudResponse expectedResponse = toResponse(s);
        SolicitudSearchFilter filter = new SolicitudSearchFilter();

        when(handler.search(filter)).thenReturn(List.of(s));
        when(mapper.toResponse(s)).thenReturn(expectedResponse);

        List<SolicitudResponse> result = svc.search(filter);

        assertThat(result).containsExactly(expectedResponse);
        verify(handler).search(filter);
        verify(mapper).toResponse(s);
    }

    @Test
    void classifyAutoAssignsDefaultClasificacionIfNull() {
        Solicitud s = buildSolicitud();
        s.setClasificacion(null);
        SolicitudResponse expectedResponse = toResponse(s);

        when(handler.get(s.getId())).thenReturn(s);
        when(handler.getDefaultClasificacion()).thenReturn(
                new Clasificacion() {{ setId(UUID.randomUUID()); setNombre("GENERICA"); }}
        );
        when(handler.update(s.getId(), s)).thenReturn(s);
        when(mapper.toResponse(s)).thenReturn(expectedResponse);

        SolicitudResponse result = svc.classifyAuto(s.getId());

        assertThat(result).isEqualTo(expectedResponse);
        assertThat(s.getClasificacion()).isNotNull();
        assertThat(s.getClasificacion().getNombre()).isEqualTo("GENERICA");

        verify(handler).get(s.getId());
        verify(handler).getDefaultClasificacion();
        verify(handler).update(s.getId(), s);
        verify(mapper).toResponse(s);
    }
}
