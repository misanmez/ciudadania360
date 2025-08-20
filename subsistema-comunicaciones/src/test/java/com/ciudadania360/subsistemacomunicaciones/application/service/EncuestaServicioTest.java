package com.ciudadania360.subsistemacomunicaciones.application.service;

import com.ciudadania360.subsistemacomunicaciones.domain.entity.Encuesta;
import com.ciudadania360.subsistemacomunicaciones.domain.handler.EncuestaHandler;
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
class EncuestaServicioTest {

    @Mock
    private EncuestaHandler handler;

    @InjectMocks
    private EncuestaServicio svc;

    private Encuesta buildEncuesta() {
        return Encuesta.builder()
                .id(UUID.randomUUID())
                .titulo("Encuesta de Satisfacción")
                .descripcion("Descripción de la encuesta")
                .preguntas("[]") // ejemplo vacío de preguntas en JSON
                .estado("ACTIVA")
                .audiencia("General")
                .fechaInicio(Instant.now())
                .fechaFin(Instant.now().plusSeconds(3600))
                .vinculadaSolicitudId(UUID.randomUUID())
                .version(1L)
                .build();
    }

    @Test
    void listDelegatesToHandler() {
        Encuesta e = buildEncuesta();
        when(handler.list()).thenReturn(List.of(e));

        List<Encuesta> result = svc.list();

        assertThat(result).containsExactly(e);
        verify(handler).list();
    }

    @Test
    void getDelegatesToHandler() {
        Encuesta e = buildEncuesta();
        when(handler.get(e.getId())).thenReturn(e);

        Encuesta result = svc.get(e.getId());

        assertThat(result).isEqualTo(e);
        verify(handler).get(e.getId());
    }

    @Test
    void createDelegatesToHandler() {
        Encuesta e = buildEncuesta();
        when(handler.create(any())).thenReturn(e);

        Encuesta result = svc.create(e);

        assertThat(result).isEqualTo(e);
        verify(handler).create(e);
    }

    @Test
    void updateDelegatesToHandler() {
        Encuesta e = buildEncuesta();
        when(handler.update(eq(e.getId()), any())).thenReturn(e);

        Encuesta result = svc.update(e.getId(), e);

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
