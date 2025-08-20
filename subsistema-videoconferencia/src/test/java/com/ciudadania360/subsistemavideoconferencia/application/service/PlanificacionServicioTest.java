package com.ciudadania360.subsistemavideoconferencia.application.service;

import com.ciudadania360.subsistemavideoconferencia.domain.entity.Planificacion;
import com.ciudadania360.subsistemavideoconferencia.domain.handler.PlanificacionHandler;
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
class PlanificacionServicioTest {

    @Mock
    private PlanificacionHandler handler;

    @InjectMocks
    private PlanificacionServicio svc;

    private Planificacion buildPlanificacion() {
        return Planificacion.builder()
                .id(UUID.randomUUID())
                .nombre("Planificación de prueba")
                .descripcion("Descripción de prueba")
                .build();
    }

    @Test
    void listDelegatesToHandler() {
        Planificacion p = buildPlanificacion();
        when(handler.list()).thenReturn(List.of(p));

        List<Planificacion> result = svc.list();

        assertThat(result).containsExactly(p);
        verify(handler).list();
    }

    @Test
    void getDelegatesToHandler() {
        Planificacion p = buildPlanificacion();
        when(handler.get(p.getId())).thenReturn(p);

        Planificacion result = svc.get(p.getId());

        assertThat(result).isEqualTo(p);
        verify(handler).get(p.getId());
    }

    @Test
    void createDelegatesToHandler() {
        Planificacion p = buildPlanificacion();
        when(handler.create(any())).thenReturn(p);

        Planificacion result = svc.create(p);

        assertThat(result).isEqualTo(p);
        verify(handler).create(p);
    }

    @Test
    void updateDelegatesToHandler() {
        Planificacion p = buildPlanificacion();
        when(handler.update(eq(p.getId()), any())).thenReturn(p);

        Planificacion result = svc.update(p.getId(), p);

        assertThat(result).isEqualTo(p);
        verify(handler).update(p.getId(), p);
    }

    @Test
    void deleteDelegatesToHandler() {
        UUID id = UUID.randomUUID();

        svc.delete(id);

        verify(handler).delete(id);
    }
}
