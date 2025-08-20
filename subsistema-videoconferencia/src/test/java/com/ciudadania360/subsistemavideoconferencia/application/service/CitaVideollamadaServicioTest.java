package com.ciudadania360.subsistemavideoconferencia.application.service;

import com.ciudadania360.subsistemavideoconferencia.domain.entity.CitaVideollamada;
import com.ciudadania360.subsistemavideoconferencia.domain.handler.CitaVideollamadaHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CitaVideollamadaServicioTest {

    @Mock
    private CitaVideollamadaHandler handler;

    @InjectMocks
    private CitaVideollamadaServicio svc;

    private CitaVideollamada buildCita() {
        return CitaVideollamada.builder()
                .id(UUID.randomUUID())
                .sesionId(UUID.randomUUID())
                .ciudadanoId(UUID.randomUUID())
                .empleadoId("empleado123")
                .fechaProgramadaInicio(Instant.parse("2023-10-01T10:00:00Z"))
                .fechaProgramadaFin(Instant.parse("2023-10-01T11:00:00Z"))
                .estado("PROGRAMADA")
                .canalNotificacion("EMAIL")
                .notas("Notas de prueba")
                .version(1L)
                .build();
    }

    @Test
    void listDelegatesToHandler() {
        CitaVideollamada c = buildCita();
        when(handler.list()).thenReturn(List.of(c));

        List<CitaVideollamada> result = svc.list();

        assertThat(result).containsExactly(c);
        verify(handler).list();
    }

    @Test
    void getDelegatesToHandler() {
        CitaVideollamada c = buildCita();
        when(handler.get(c.getId())).thenReturn(c);

        CitaVideollamada result = svc.get(c.getId());

        assertThat(result).isEqualTo(c);
        verify(handler).get(c.getId());
    }

    @Test
    void createDelegatesToHandler() {
        CitaVideollamada c = buildCita();
        when(handler.create(any())).thenReturn(c);

        CitaVideollamada result = svc.create(c);

        assertThat(result).isEqualTo(c);
        verify(handler).create(c);
    }

    @Test
    void updateDelegatesToHandler() {
        CitaVideollamada c = buildCita();
        when(handler.update(eq(c.getId()), any())).thenReturn(c);

        CitaVideollamada result = svc.update(c.getId(), c);

        assertThat(result).isEqualTo(c);
        verify(handler).update(c.getId(), c);
    }

    @Test
    void deleteDelegatesToHandler() {
        UUID id = UUID.randomUUID();

        svc.delete(id);

        verify(handler).delete(id);
    }
}
