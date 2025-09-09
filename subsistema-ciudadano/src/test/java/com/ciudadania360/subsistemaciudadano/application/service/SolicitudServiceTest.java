package com.ciudadania360.subsistemaciudadano.application.service;

import com.ciudadania360.shared.application.dto.solicitud.SolicitudRequest;
import com.ciudadania360.shared.application.dto.solicitud.SolicitudResponse;
import com.ciudadania360.shared.application.dto.ciudadano.CiudadanoResponse;
import com.ciudadania360.shared.application.dto.clasificacion.ClasificacionResponse;
import com.ciudadania360.shared.application.dto.interaccion.InteraccionResponse;
import com.ciudadania360.subsistemaciudadano.application.mapper.SolicitudMapper;
import com.ciudadania360.subsistemaciudadano.application.validator.SolicitudValidator;
import com.ciudadania360.subsistemaciudadano.domain.entity.Solicitud;
import com.ciudadania360.subsistemaciudadano.domain.entity.Ciudadano;
import com.ciudadania360.subsistemaciudadano.domain.handler.SolicitudHandler;
import com.ciudadania360.shared.application.dto.empleado.EmpleadoResponse;
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
    @Mock private SolicitudValidator validator;

    @InjectMocks private SolicitudService svc;

    private Solicitud buildSolicitud() {
        Ciudadano ciudadano = new Ciudadano();
        ciudadano.setId(UUID.randomUUID());

        return Solicitud.builder()
                .id(UUID.fromString("00000000-0000-0000-0000-000000000001"))
                .ciudadano(ciudadano)
                .titulo("Título de prueba")
                .descripcion("Descripción de prueba")
                .tipo("TipoA")
                .canalEntrada("Web")
                .estado("Abierta")
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
        SolicitudRequest req = new SolicitudRequest();
        req.setCiudadanoId(s.getCiudadano().getId());
        req.setTitulo(s.getTitulo());
        req.setDescripcion(s.getDescripcion());
        req.setTipo(s.getTipo());
        req.setCanalEntrada(s.getCanalEntrada());
        req.setEstado(s.getEstado());
        req.setPrioridad(s.getPrioridad());
        req.setNumeroExpediente(s.getNumeroExpediente());
        req.setFechaRegistro(s.getFechaRegistro());
        req.setFechaLimiteSLA(s.getFechaLimiteSLA());
        req.setOrigen(s.getOrigen());
        req.setReferenciaExterna(s.getReferenciaExterna());
        req.setMetadata(s.getMetadata());
        return req;
    }

    private SolicitudResponse toResponse(Solicitud s) {
        return SolicitudResponse.builder()
                .id(s.getId())
                .ciudadano(s.getCiudadano() != null ? CiudadanoResponse.builder()
                        .id(s.getCiudadano().getId())
                        .nombre(s.getCiudadano().getNombre())
                        .apellidos(s.getCiudadano().getApellidos())
                        .email(s.getCiudadano().getEmail())
                        .telefono(s.getCiudadano().getTelefono())
                        .build() : null)
                .titulo(s.getTitulo())
                .descripcion(s.getDescripcion())
                .tipo(s.getTipo())
                .canalEntrada(s.getCanalEntrada())
                .estado(s.getEstado())
                .prioridad(s.getPrioridad())
                .clasificacion(s.getClasificacion() != null ? ClasificacionResponse.builder()
                        .id(s.getClasificacion().getId())
                        .nombre(s.getClasificacion().getNombre())
                        .descripcion(s.getClasificacion().getDescripcion())
                        .build() : null)
                .ubicacionId(s.getUbicacion() != null ? s.getUbicacion().getId() : null)
                .numeroExpediente(s.getNumeroExpediente())
                .fechaRegistro(s.getFechaRegistro())
                .fechaLimiteSLA(s.getFechaLimiteSLA())
                .fechaCierre(s.getFechaCierre())
                .scoreRelevancia(s.getScoreRelevancia())
                .origen(s.getOrigen())
                .adjuntosCount(s.getAdjuntosCount())
                .encuestaEnviada(s.getEncuestaEnviada())
                .referenciaExterna(s.getReferenciaExterna())
                .metadata(s.getMetadata())
                .agenteAsignado(s.getAgenteAsignado() != null ? EmpleadoResponse.builder()
                        .id(s.getAgenteAsignado().getId())
                        .nombre(s.getAgenteAsignado().getNombre())
                        .apellidos(s.getAgenteAsignado().getApellidos())
                        .email(s.getAgenteAsignado().getEmail())
                        .telefono(s.getAgenteAsignado().getTelefono())
                        .rol(s.getAgenteAsignado().getRol())
                        .build() : null)
                .interacciones(s.getInteracciones() != null ? s.getInteracciones().stream()
                        .map(i -> InteraccionResponse.builder()
                                .id(i.getId())
                                .mensaje(i.getMensaje())
                                .fecha(i.getFecha())
                                .build())
                        .toList() : null)
                .version(s.getVersion())
                .build();

    }

    @Test
    void listDelegatesToHandler() {
        Solicitud s = buildSolicitud();
        SolicitudResponse r = toResponse(s);

        when(handler.list()).thenReturn(List.of(s));
        when(mapper.toResponse(s)).thenReturn(r);

        List<SolicitudResponse> result = svc.list();

        assertThat(result).containsExactly(r);
        verify(handler).list();
        verify(mapper).toResponse(s);
        verifyNoMoreInteractions(handler, mapper);
    }

    @Test
    void getDelegatesToHandler() {
        Solicitud s = buildSolicitud();
        SolicitudResponse r = toResponse(s);

        when(handler.get(s.getId())).thenReturn(s);
        when(mapper.toResponse(s)).thenReturn(r);

        SolicitudResponse result = svc.get(s.getId());

        assertThat(result).isEqualTo(r);
        verify(handler).get(s.getId());
        verify(mapper).toResponse(s);
    }

    @Test
    void createDelegatesToHandlerAndValidator() {
        Solicitud s = buildSolicitud();
        SolicitudRequest req = toRequest(s);
        SolicitudResponse r = toResponse(s);

        doNothing().when(validator).validateCreate(req);
        when(mapper.toEntity(req)).thenReturn(s);
        when(handler.create(s)).thenReturn(s);
        when(mapper.toResponse(s)).thenReturn(r);

        SolicitudResponse result = svc.create(req);

        assertThat(result).isEqualTo(r);
        verify(validator).validateCreate(req);
        verify(mapper).toEntity(req);
        verify(handler).create(s);
        verify(mapper).toResponse(s);
    }

    @Test
    void updateDelegatesToHandlerAndValidator() {
        Solicitud s = buildSolicitud();
        SolicitudRequest req = toRequest(s);
        req.setPrioridad("MEDIA");

        doNothing().when(validator).validateUpdate(req);
        when(handler.get(s.getId())).thenReturn(s);
        doNothing().when(mapper).updateEntity(s, req);
        when(handler.update(s.getId(), s)).thenReturn(s);
        when(mapper.toResponse(s)).thenReturn(toResponse(s));

        SolicitudResponse result = svc.update(s.getId(), req);

        assertThat(result).isEqualTo(toResponse(s));
        verify(validator).validateUpdate(req);
        verify(handler).get(s.getId());
        verify(mapper).updateEntity(s, req);
        verify(handler).update(s.getId(), s);
        verify(mapper).toResponse(s);
    }

    @Test
    void deleteDelegatesToHandlerAndValidator() {
        Solicitud s = buildSolicitud();

        when(handler.get(s.getId())).thenReturn(s);
        doNothing().when(validator).validateDelete(any(Solicitud.class));
        doNothing().when(handler).delete(s.getId());

        svc.delete(s.getId());

        verify(handler).get(s.getId());
        verify(validator).validateDelete(any(Solicitud.class));
        verify(handler).delete(s.getId());
    }
}
