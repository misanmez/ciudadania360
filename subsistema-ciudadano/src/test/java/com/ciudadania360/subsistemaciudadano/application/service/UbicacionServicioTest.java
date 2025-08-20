package com.ciudadania360.subsistemaciudadano.application.service;

import com.ciudadania360.subsistemaciudadano.domain.entity.Ubicacion;
import com.ciudadania360.subsistemaciudadano.domain.handler.UbicacionHandler;
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
class UbicacionServicioTest {

    @Mock
    private UbicacionHandler handler;

    @InjectMocks
    private UbicacionServicio svc;

    private Ubicacion buildUbicacion() {
        return Ubicacion.builder()
                .id(UUID.randomUUID())
                .direccion("Calle Falsa 123")
                .municipio("Madrid")
                .provincia("Madrid")
                .cp("28080")
                .lat(40.4168)
                .lon(-3.7038)
                .precision(1)
                .fuente("Manual")
                .version(1L)
                .build();
    }

    @Test
    void obtenerTodosDelegatesToHandler() {
        Ubicacion u = buildUbicacion();
        when(handler.obtenerTodos()).thenReturn(List.of(u));

        List<Ubicacion> result = svc.obtenerTodos();

        assertThat(result).containsExactly(u);
        verify(handler).obtenerTodos();
    }

    @Test
    void obtenerPorIdDelegatesToHandler() {
        Ubicacion u = buildUbicacion();
        when(handler.obtenerPorId(u.getId())).thenReturn(u);

        Ubicacion result = svc.obtenerPorId(u.getId());

        assertThat(result).isEqualTo(u);
        verify(handler).obtenerPorId(u.getId());
    }

    @Test
    void crearDelegatesToHandler() {
        Ubicacion u = buildUbicacion();
        when(handler.crear(any())).thenReturn(u);

        Ubicacion result = svc.crear(u);

        assertThat(result).isEqualTo(u);
        verify(handler).crear(u);
    }

    @Test
    void actualizarDelegatesToHandler() {
        Ubicacion u = buildUbicacion();
        when(handler.actualizar(eq(u.getId()), any())).thenReturn(u);

        Ubicacion result = svc.actualizar(u.getId(), u);

        assertThat(result).isEqualTo(u);
        verify(handler).actualizar(u.getId(), u);
    }

    @Test
    void eliminarDelegatesToHandler() {
        UUID id = UUID.randomUUID();

        svc.eliminar(id);

        verify(handler).eliminar(id);
    }
}
