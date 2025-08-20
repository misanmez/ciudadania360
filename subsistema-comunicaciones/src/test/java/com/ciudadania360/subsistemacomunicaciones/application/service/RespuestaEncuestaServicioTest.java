package com.ciudadania360.subsistemacomunicaciones.application.service;

import com.ciudadania360.subsistemacomunicaciones.domain.entity.RespuestaEncuesta;
import com.ciudadania360.subsistemacomunicaciones.domain.handler.RespuestaEncuestaHandler;
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
class RespuestaEncuestaServicioTest {

    @Mock
    private RespuestaEncuestaHandler handler;

    @InjectMocks
    private RespuestaEncuestaServicio svc;

    private RespuestaEncuesta buildRespuesta() {
        return RespuestaEncuesta.builder()
                .id(UUID.randomUUID())
                .encuestaId(UUID.randomUUID())
                .ciudadanoId(UUID.randomUUID())
                .respuestas("{\"pregunta1\":\"respuesta\"}")
                .puntuacion(5)
                .comentarios("Respuesta de ejemplo")
                .fecha(Instant.now())
                .version(1L)
                .build();
    }

    @Test
    void listDelegatesToHandler() {
        RespuestaEncuesta e = buildRespuesta();
        when(handler.list()).thenReturn(List.of(e));

        List<RespuestaEncuesta> result = svc.list();

        assertThat(result).containsExactly(e);
        verify(handler).list();
    }

    @Test
    void getDelegatesToHandler() {
        RespuestaEncuesta e = buildRespuesta();
        when(handler.get(e.getId())).thenReturn(e);

        RespuestaEncuesta result = svc.get(e.getId());

        assertThat(result).isEqualTo(e);
        verify(handler).get(e.getId());
    }

    @Test
    void createDelegatesToHandler() {
        RespuestaEncuesta e = buildRespuesta();
        when(handler.create(any())).thenReturn(e);

        RespuestaEncuesta result = svc.create(e);

        assertThat(result).isEqualTo(e);
        verify(handler).create(e);
    }

    @Test
    void updateDelegatesToHandler() {
        RespuestaEncuesta e = buildRespuesta();
        when(handler.update(eq(e.getId()), any())).thenReturn(e);

        RespuestaEncuesta result = svc.update(e.getId(), e);

        assertThat(result).isEqualTo(e);
        verify(handler).update(e.getId(), e);
    }

    @Test
    void deleteDelegatesToHandler() {
        UUID id = UUID.randomUUID();

        svc.delete(id);

        verify(handler).delete(id);
    }
}
