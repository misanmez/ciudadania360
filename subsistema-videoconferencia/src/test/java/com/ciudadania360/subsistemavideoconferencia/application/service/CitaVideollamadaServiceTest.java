package com.ciudadania360.subsistemavideoconferencia.application.service;

import com.ciudadania360.subsistemavideoconferencia.application.dto.citavideollamada.CitaVideollamadaRequest;
import com.ciudadania360.subsistemavideoconferencia.application.dto.citavideollamada.CitaVideollamadaResponse;
import com.ciudadania360.subsistemavideoconferencia.application.mapper.CitaVideollamadaMapper;
import com.ciudadania360.subsistemavideoconferencia.domain.entity.CitaVideollamada;
import com.ciudadania360.subsistemavideoconferencia.domain.handler.CitaVideollamadaHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class CitaVideollamadaServiceTest {

    @Mock private CitaVideollamadaHandler handler;
    @Mock private CitaVideollamadaMapper mapper;
    @InjectMocks private CitaVideollamadaService svc;

    private static final UUID FIXED_ID = UUID.fromString("11111111-1111-1111-1111-111111111111");
    private static final Instant FIXED_INSTANT = Instant.parse("2025-08-22T09:02:58.214242400Z");

    private CitaVideollamada buildEntity() {
        return CitaVideollamada.builder()
                .id(FIXED_ID)
                .sesionId(UUID.randomUUID())
                .ciudadanoId(UUID.randomUUID())
                .empleadoId("EMP123")
                .fechaProgramadaInicio(FIXED_INSTANT)
                .fechaProgramadaFin(FIXED_INSTANT.plusSeconds(3600))
                .estado("Pendiente")
                .canalNotificacion("Email")
                .notas("Notas de prueba")
                .version(1L)
                .build();
    }

    private CitaVideollamadaRequest toRequest(CitaVideollamada e) {
        return new CitaVideollamadaRequest(
                e.getSesionId(),
                e.getCiudadanoId(),
                e.getEmpleadoId(),
                e.getFechaProgramadaInicio(),
                e.getFechaProgramadaFin(),
                e.getEstado(),
                e.getCanalNotificacion(),
                e.getNotas()
        );
    }

    private CitaVideollamadaResponse toResponse(CitaVideollamada e) {
        return new CitaVideollamadaResponse(
                e.getId(),
                e.getSesionId(),
                e.getCiudadanoId(),
                e.getEmpleadoId(),
                e.getFechaProgramadaInicio(),
                e.getFechaProgramadaFin(),
                e.getEstado(),
                e.getCanalNotificacion(),
                e.getNotas()
        );
    }

    @Test void listDelegatesToHandler() {
        CitaVideollamada e = buildEntity();
        when(handler.list()).thenReturn(List.of(e));
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        List<CitaVideollamadaResponse> result = svc.list();

        assertThat(result).containsExactly(toResponse(e));
        verify(handler).list();
        verify(mapper).toResponse(e);
    }

    @Test void getDelegatesToHandler() {
        CitaVideollamada e = buildEntity();
        when(handler.get(e.getId())).thenReturn(e);
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        CitaVideollamadaResponse result = svc.get(e.getId());

        assertThat(result).isEqualTo(toResponse(e));
        verify(handler).get(e.getId());
        verify(mapper).toResponse(e);
    }

    @Test void createDelegatesToHandler() {
        CitaVideollamada e = buildEntity();
        CitaVideollamadaRequest request = toRequest(e);

        when(mapper.toEntity(request)).thenReturn(e);
        when(handler.create(any(CitaVideollamada.class))).thenReturn(e);
        when(mapper.toResponse(any(CitaVideollamada.class))).thenReturn(toResponse(e));

        CitaVideollamadaResponse result = svc.create(request);

        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(toResponse(e));

        verify(mapper).toEntity(request);
        verify(handler).create(any(CitaVideollamada.class));
        verify(mapper).toResponse(any(CitaVideollamada.class));
    }

    @Test void updateDelegatesToHandler() {
        CitaVideollamada e = buildEntity();
        CitaVideollamadaRequest request = toRequest(e);

        when(handler.get(e.getId())).thenReturn(e);
        doNothing().when(mapper).updateEntity(e, request);
        when(handler.update(eq(e.getId()), same(e))).thenReturn(e);
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        CitaVideollamadaResponse result = svc.update(e.getId(), request);

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
