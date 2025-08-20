package com.ciudadania360.subsistemaciudadano.application.service;

import com.ciudadania360.subsistemaciudadano.domain.entity.ReglaClasificacion;
import com.ciudadania360.subsistemaciudadano.domain.handler.ReglaClasificacionHandler;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReglaClasificacionServicioTest {

    @Mock
    private ReglaClasificacionHandler handler;

    @InjectMocks
    private ReglaClasificacionServicio svc;

    private ReglaClasificacion buildRegla() {
        return ReglaClasificacion.builder()
                .id(UUID.randomUUID())
                .nombre("Regla de prueba")
                .expresion("x > 10")
                .prioridad(1)
                .activa(true)
                .clasificacionId(UUID.randomUUID())
                .condiciones("{}")
                .fuente("Sistema")
                .vigenciaDesde(Instant.now())
                .vigenciaHasta(Instant.now().plusSeconds(3600))
                .version(1L)
                .build();
    }

    @Test
    void obtenerTodosDelegatesToHandler() {
        ReglaClasificacion e = buildRegla();
        when(handler.obtenerTodos()).thenReturn(List.of(e));

        List<ReglaClasificacion> result = svc.obtenerTodos();

        assertThat(result).containsExactly(e);
        verify(handler).obtenerTodos();
    }

    @Test
    void obtenerPorIdDelegatesToHandler() {
        ReglaClasificacion e = buildRegla();
        when(handler.obtenerPorId(e.getId())).thenReturn(e);

        ReglaClasificacion result = svc.obtenerPorId(e.getId());

        assertThat(result).isEqualTo(e);
        verify(handler).obtenerPorId(e.getId());
    }

    @Test
    void crearDelegatesToHandler() {
        ReglaClasificacion e = buildRegla();
        when(handler.crear(any())).thenReturn(e);

        ReglaClasificacion result = svc.crear(e);

        assertThat(result).isEqualTo(e);
        verify(handler).crear(e);
    }

    @Test
    void actualizarDelegatesToHandler() {
        ReglaClasificacion e = buildRegla();
        when(handler.actualizar(eq(e.getId()), any())).thenReturn(e);

        ReglaClasificacion result = svc.actualizar(e.getId(), e);

        assertThat(result).isEqualTo(e);
        verify(handler).actualizar(e.getId(), e);
    }

    @Test
    void eliminarDelegatesToHandler() {
        UUID id = UUID.randomUUID();

        svc.eliminar(id);

        verify(handler).eliminar(id);
    }
}
