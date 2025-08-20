package com.ciudadania360.subsistemaciudadano.application.service;

import com.ciudadania360.subsistemaciudadano.domain.entity.Clasificacion;
import com.ciudadania360.subsistemaciudadano.domain.handler.ClasificacionHandler;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClasificacionServicioTest {

    @Mock
    private ClasificacionHandler handler;

    @InjectMocks
    private ClasificacionServicio svc;

    private Clasificacion buildClasificacion() {
        return Clasificacion.builder()
                .id(UUID.randomUUID())
                .codigo("C001")
                .nombre("Clasificación de prueba")
                .descripcion("Descripción de prueba")
                .tipo("TIPO")
                .padre(null)
                .hijos(List.of()) // lista vacía por defecto
                .version(1L)
                .build();
    }

    @Test
    void obtenerTodosDelegatesToHandler() {
        Clasificacion e = buildClasificacion();
        when(handler.obtenerTodos()).thenReturn(List.of(e));

        List<Clasificacion> result = svc.obtenerTodos();

        assertThat(result).containsExactly(e);
        verify(handler).obtenerTodos();
    }

    @Test
    void obtenerPorIdDelegatesToHandler() {
        Clasificacion e = buildClasificacion();
        when(handler.obtenerPorId(e.getId())).thenReturn(e);

        Clasificacion result = svc.obtenerPorId(e.getId());

        assertThat(result).isEqualTo(e);
        verify(handler).obtenerPorId(e.getId());
    }

    @Test
    void crearDelegatesToHandler() {
        Clasificacion e = buildClasificacion();
        when(handler.crear(any())).thenReturn(e);

        Clasificacion result = svc.crear(e);

        assertThat(result).isEqualTo(e);
        verify(handler).crear(e);
    }

    @Test
    void actualizarDelegatesToHandler() {
        Clasificacion e = buildClasificacion();
        when(handler.actualizar(eq(e.getId()), any())).thenReturn(e);

        Clasificacion result = svc.actualizar(e.getId(), e);

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
