package com.ciudadania360.subsistemaciudadano.application.service;

import com.ciudadania360.subsistemaciudadano.application.dto.InteraccionRequest;
import com.ciudadania360.subsistemaciudadano.application.dto.InteraccionResponse;
import com.ciudadania360.subsistemaciudadano.application.mapper.InteraccionMapper;
import com.ciudadania360.subsistemaciudadano.domain.entity.Ciudadano;
import com.ciudadania360.subsistemaciudadano.domain.entity.Interaccion;
import com.ciudadania360.subsistemaciudadano.domain.entity.Solicitud;
import com.ciudadania360.subsistemaciudadano.domain.handler.InteraccionHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InteraccionServiceTest {

    @Mock
    private InteraccionHandler handler;

    @Mock
    private InteraccionMapper mapper;

    @InjectMocks
    private InteraccionService service;

    private Interaccion buildInteraccion() {
        UUID ciudadanoId = UUID.fromString("00000000-0000-0000-0000-000000000001");
        UUID solicitudId = UUID.fromString("00000000-0000-0000-0000-000000000002");
        UUID interaccionId = UUID.fromString("00000000-0000-0000-0000-000000000003");

        Ciudadano ciudadano = Ciudadano.builder()
                .id(ciudadanoId)
                .nombre("Juan")
                .apellidos("Pérez")
                .email("juan@example.com")
                .build();

        Solicitud solicitud = Solicitud.builder()
                .id(solicitudId)
                .build();

        return Interaccion.builder()
                .id(interaccionId)
                .ciudadano(ciudadano)
                .solicitud(solicitud)
                .canal("email")
                .fecha(Instant.parse("2025-01-01T10:00:00Z"))
                .agente("Agente1")
                .mensaje("Mensaje de prueba")
                .adjuntoUri(null)
                .visibilidad("PUBLICO")
                .version(1L)
                .build();
    }

    private InteraccionRequest toRequest(Interaccion e) {
        return new InteraccionRequest(
                e.getCiudadano().getId(),
                e.getSolicitud().getId(),
                e.getCanal(),
                e.getFecha(),
                e.getAgente(),
                e.getMensaje(),
                e.getAdjuntoUri(),
                e.getVisibilidad()
        );
    }

    private InteraccionResponse toResponse(Interaccion e) {
        return new InteraccionResponse(
                e.getId(),
                e.getCiudadano().getId(),
                e.getSolicitud().getId(),
                e.getCanal(),
                e.getFecha(),
                e.getAgente(),
                e.getMensaje(),
                e.getAdjuntoUri(),
                e.getVisibilidad()
        );
    }

    @Test
    void listDelegatesToHandler() {
        Interaccion e = buildInteraccion();
        InteraccionResponse expectedResponse = toResponse(e);

        when(handler.list()).thenReturn(List.of(e));
        when(mapper.toResponse(e)).thenReturn(expectedResponse);

        List<InteraccionResponse> result = service.list();

        assertThat(result).containsExactly(expectedResponse);
        verify(handler).list();
        verify(mapper).toResponse(e);
    }

    @Test
    void getDelegatesToHandler() {
        Interaccion e = buildInteraccion();
        InteraccionResponse expectedResponse = toResponse(e);

        when(handler.get(e.getId())).thenReturn(e);
        when(mapper.toResponse(e)).thenReturn(expectedResponse);

        InteraccionResponse result = service.get(e.getId());

        assertThat(result).isEqualTo(expectedResponse);
        verify(handler).get(e.getId());
        verify(mapper).toResponse(e);
    }

    @Test
    void createDelegatesToHandler() {
        Interaccion e = buildInteraccion();
        InteraccionRequest request = toRequest(e);
        InteraccionResponse expectedResponse = toResponse(e);

        when(mapper.toEntity(request)).thenReturn(e);
        when(handler.create(e)).thenReturn(e);
        when(mapper.toResponse(e)).thenReturn(expectedResponse);

        InteraccionResponse result = service.create(request);

        assertThat(result).isEqualTo(expectedResponse);
        verify(mapper).toEntity(request);
        verify(handler).create(e);
        verify(mapper).toResponse(e);
    }

    @Test
    void updateDelegatesToHandler() {
        Interaccion e = buildInteraccion();
        InteraccionRequest request = toRequest(e);
        InteraccionResponse expectedResponse = toResponse(e);

        when(handler.get(e.getId())).thenReturn(e);
        doNothing().when(mapper).updateEntity(e, request);
        when(handler.update(e.getId(), e)).thenReturn(e);
        when(mapper.toResponse(e)).thenReturn(expectedResponse);

        InteraccionResponse result = service.update(e.getId(), request);

        assertThat(result).isEqualTo(expectedResponse);
        verify(handler).get(e.getId());
        verify(mapper).updateEntity(e, request);
        verify(handler).update(e.getId(), e);
        verify(mapper).toResponse(e);
    }

    @Test
    void deleteDelegatesToHandler() {
        UUID id = UUID.fromString("00000000-0000-0000-0000-000000000004");

        service.delete(id);

        verify(handler).delete(id);
        verifyNoInteractions(mapper);
    }
}
