package com.ciudadania360.subsistemaciudadano.application.service;

import com.ciudadania360.subsistemaciudadano.application.dto.solicitudestadohistorial.SolicitudEstadoHistorialRequest;
import com.ciudadania360.subsistemaciudadano.application.dto.solicitudestadohistorial.SolicitudEstadoHistorialResponse;
import com.ciudadania360.subsistemaciudadano.application.mapper.SolicitudEstadoHistorialMapper;
import com.ciudadania360.subsistemaciudadano.application.validator.SolicitudEstadoHistorialValidator;
import com.ciudadania360.subsistemaciudadano.domain.entity.Solicitud;
import com.ciudadania360.subsistemaciudadano.domain.entity.SolicitudEstadoHistorial;
import com.ciudadania360.subsistemaciudadano.domain.handler.SolicitudEstadoHistorialHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class SolicitudEstadoHistorialServiceTest {

    @Mock private SolicitudEstadoHistorialHandler handler;
    @Mock private SolicitudEstadoHistorialMapper mapper;
    @Mock private SolicitudEstadoHistorialValidator validator; // <-- agregado
    @InjectMocks private SolicitudEstadoHistorialService svc;

    private SolicitudEstadoHistorial buildHistorial() {
        return SolicitudEstadoHistorial.builder()
                .id(UUID.randomUUID())
                .solicitud(Solicitud.builder().id(UUID.randomUUID()).build())
                .estadoAnterior("PENDIENTE")
                .estadoNuevo("COMPLETADA")
                .fechaCambio(Instant.now())
                .agente("usuario123")
                .metadata("{\"nota\":\"test\"}")
                .version(1L)
                .build();
    }

    private SolicitudEstadoHistorialRequest toRequest(SolicitudEstadoHistorial e) {
        return new SolicitudEstadoHistorialRequest(
                e.getSolicitud() != null ? e.getSolicitud().getId() : null,
                e.getEstadoAnterior(),
                e.getEstadoNuevo(),
                e.getFechaCambio(),
                e.getAgente(),
                e.getMetadata()
        );
    }

    private SolicitudEstadoHistorialResponse toResponse(SolicitudEstadoHistorial e) {
        return new SolicitudEstadoHistorialResponse(
                e.getId(),
                e.getSolicitud() != null ? e.getSolicitud().getId() : null,
                e.getEstadoAnterior(),
                e.getEstadoNuevo(),
                e.getFechaCambio(),
                e.getAgente(),
                e.getMetadata(),
                e.getVersion()
        );
    }

    @Test void listDelegatesToHandler() {
        SolicitudEstadoHistorial e = buildHistorial();
        when(handler.list()).thenReturn(List.of(e));
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        List<SolicitudEstadoHistorialResponse> result = svc.list();

        assertThat(result).containsExactly(toResponse(e));
        verify(handler).list();
        verify(mapper).toResponse(e);
    }

    @Test void getDelegatesToHandler() {
        SolicitudEstadoHistorial e = buildHistorial();
        when(handler.get(e.getId())).thenReturn(e);
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        SolicitudEstadoHistorialResponse result = svc.get(e.getId());

        assertThat(result).isEqualTo(toResponse(e));
        verify(handler).get(e.getId());
        verify(mapper).toResponse(e);
    }

    @Test void createDelegatesToHandler() {
        SolicitudEstadoHistorial e = buildHistorial();
        SolicitudEstadoHistorialRequest request = toRequest(e);
        SolicitudEstadoHistorialResponse expectedResponse = toResponse(e);

        doNothing().when(validator).validateCreate(request); // <-- stub validator
        when(mapper.toEntity(request)).thenReturn(e);
        when(handler.create(e)).thenReturn(e);
        when(mapper.toResponse(e)).thenReturn(expectedResponse);

        SolicitudEstadoHistorialResponse result = svc.create(request);

        assertThat(result).isEqualTo(expectedResponse);
        verify(validator).validateCreate(request);
        verify(mapper).toEntity(request);
        verify(handler).create(e);
        verify(mapper).toResponse(e);
    }

    @Test void updateDelegatesToHandler() {
        SolicitudEstadoHistorial e = buildHistorial();
        SolicitudEstadoHistorialRequest request = toRequest(e);

        doNothing().when(validator).validateUpdate(request); // <-- stub validator
        when(handler.get(e.getId())).thenReturn(e);
        doNothing().when(mapper).updateEntity(any(SolicitudEstadoHistorial.class), eq(request));
        when(handler.update(eq(e.getId()), any(SolicitudEstadoHistorial.class))).thenReturn(e);
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        SolicitudEstadoHistorialResponse result = svc.update(e.getId(), request);

        assertThat(result).isEqualTo(toResponse(e));
        verify(validator).validateUpdate(request);
        verify(handler).get(e.getId());
        verify(mapper).updateEntity(same(e), eq(request));
        verify(handler).update(eq(e.getId()), same(e));
        verify(mapper).toResponse(e);
    }

    @Test void deleteDelegatesToHandler() {
        SolicitudEstadoHistorial e = buildHistorial();
        when(handler.get(e.getId())).thenReturn(e);
        doNothing().when(validator).validateDelete(e); // <-- stub validator

        svc.delete(e.getId());

        verify(handler).get(e.getId());
        verify(validator).validateDelete(e);
        verify(handler).delete(e.getId());
    }
}
