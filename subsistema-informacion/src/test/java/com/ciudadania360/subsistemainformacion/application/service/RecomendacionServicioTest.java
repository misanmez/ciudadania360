package com.ciudadania360.subsistemainformacion.application.service;

import com.ciudadania360.subsistemainformacion.domain.entity.Recomendacion;
import com.ciudadania360.subsistemainformacion.domain.handler.RecomendacionHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RecomendacionServicioTest {

    @Mock
    private RecomendacionHandler handler;

    @InjectMocks
    private RecomendacionServicio svc;

    private Recomendacion buildRecomendacion() {
        return Recomendacion.builder()
                .id(UUID.randomUUID())
                .titulo("Recomendación de Ejemplo")
                .detalle("Detalle de la recomendación")
                .build();
    }

    @Test
    void listDelegatesToHandler() {
        Recomendacion r = buildRecomendacion();
        when(handler.list()).thenReturn(List.of(r));

        List<Recomendacion> result = svc.list();

        assertThat(result).containsExactly(r);
        verify(handler).list();
    }

    @Test
    void getDelegatesToHandler() {
        Recomendacion r = buildRecomendacion();
        when(handler.get(r.getId())).thenReturn(r);

        Recomendacion result = svc.get(r.getId());

        assertThat(result).isEqualTo(r);
        verify(handler).get(r.getId());
    }

    @Test
    void createDelegatesToHandler() {
        Recomendacion r = buildRecomendacion();
        when(handler.create(any())).thenReturn(r);

        Recomendacion result = svc.create(r);

        assertThat(result).isEqualTo(r);
        verify(handler).create(r);
    }

    @Test
    void updateDelegatesToHandler() {
        Recomendacion r = buildRecomendacion();
        when(handler.update(eq(r.getId()), any())).thenReturn(r);

        Recomendacion result = svc.update(r.getId(), r);

        assertThat(result).isEqualTo(r);
        verify(handler).update(r.getId(), r);
    }

    @Test
    void deleteDelegatesToHandler() {
        UUID id = UUID.randomUUID();

        svc.delete(id);

        verify(handler).delete(id);
    }
}
