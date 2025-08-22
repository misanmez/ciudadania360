package com.ciudadania360.subsistemavideoconferencia.application.service;

import com.ciudadania360.subsistemavideoconferencia.application.dto.sesion.SesionRequest;
import com.ciudadania360.subsistemavideoconferencia.application.dto.sesion.SesionResponse;
import com.ciudadania360.subsistemavideoconferencia.application.mapper.SesionMapper;
import com.ciudadania360.subsistemavideoconferencia.domain.entity.Sesion;
import com.ciudadania360.subsistemavideoconferencia.domain.handler.SesionHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class SesionServiceTest {

    @Mock private SesionHandler handler;
    @Mock private SesionMapper mapper;
    @InjectMocks private SesionService svc;

    private static final UUID FIXED_ID = UUID.fromString("11111111-1111-1111-1111-111111111111");
    private static final Instant FIXED_INSTANT = Instant.parse("2025-08-22T09:02:58.214242400Z");

    private Sesion buildEntity() {
        return Sesion.builder()
                .id(FIXED_ID)
                .solicitudId(UUID.randomUUID())
                .fechaInicio(FIXED_INSTANT)
                .fechaFin(FIXED_INSTANT.plusSeconds(3600))
                .estado("Programada")
                .plataforma("Zoom")
                .enlace("https://zoom.us/j/123456789")
                .codigoAcceso("ABC123")
                .grabacionUri(null)
                .motivo("Reunión de prueba")
                .operadorId("OP123")
                .version(1L)
                .build();
    }

    private SesionRequest toRequest(Sesion e) {
        return new SesionRequest(
                e.getSolicitudId(),
                e.getFechaInicio(),
                e.getFechaFin(),
                e.getEstado(),
                e.getPlataforma(),
                e.getEnlace(),
                e.getCodigoAcceso(),
                e.getGrabacionUri(),
                e.getMotivo(),
                e.getOperadorId()
        );
    }

    private SesionResponse toResponse(Sesion e) {
        return new SesionResponse(
                e.getId(),
                e.getSolicitudId(),
                e.getFechaInicio(),
                e.getFechaFin(),
                e.getEstado(),
                e.getPlataforma(),
                e.getEnlace(),
                e.getCodigoAcceso(),
                e.getGrabacionUri(),
                e.getMotivo(),
                e.getOperadorId()
        );
    }

    @Test void listDelegatesToHandler() {
        Sesion e = buildEntity();
        when(handler.list()).thenReturn(List.of(e));
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        List<SesionResponse> result = svc.list();

        assertThat(result).containsExactly(toResponse(e));
        verify(handler).list();
        verify(mapper).toResponse(e);
    }

    @Test void getDelegatesToHandler() {
        Sesion e = buildEntity();
        when(handler.get(e.getId())).thenReturn(e);
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        SesionResponse result = svc.get(e.getId());

        assertThat(result).isEqualTo(toResponse(e));
        verify(handler).get(e.getId());
        verify(mapper).toResponse(e);
    }

    @Test void createDelegatesToHandler() {
        Sesion e = buildEntity();
        SesionRequest request = toRequest(e);

        when(mapper.toEntity(request)).thenReturn(e);
        when(handler.create(any(Sesion.class))).thenReturn(e);
        when(mapper.toResponse(any(Sesion.class))).thenReturn(toResponse(e));

        SesionResponse result = svc.create(request);

        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(toResponse(e));

        verify(mapper).toEntity(request);
        verify(handler).create(any(Sesion.class));
        verify(mapper).toResponse(any(Sesion.class));
    }

    @Test void updateDelegatesToHandler() {
        Sesion e = buildEntity();
        SesionRequest request = toRequest(e);

        when(handler.get(e.getId())).thenReturn(e);
        doNothing().when(mapper).updateEntity(e, request);
        when(handler.update(eq(e.getId()), same(e))).thenReturn(e);
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        SesionResponse result = svc.update(e.getId(), request);

        assertThat(result).isEqualTo(toResponse(e));
        verify(handler).get(e.getId());
        verify(mapper).updateEntity(e, request);
        verify(handler).update(e.getId(), e);
        verify(mapper).toResponse(e);
    }

    @Test void deleteDelegatesToHandler() {
        UUID id = UUID.randomUUID();
        svc.delete(id);
        verify(handler).delete(id);
        verifyNoInteractions(mapper);
    }
}
