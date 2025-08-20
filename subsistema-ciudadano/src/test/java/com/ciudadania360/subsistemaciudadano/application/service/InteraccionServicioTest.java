package com.ciudadania360.subsistemaciudadano.application.service;

import com.ciudadania360.subsistemaciudadano.domain.entity.Ciudadano;
import com.ciudadania360.subsistemaciudadano.domain.entity.Interaccion;
import com.ciudadania360.subsistemaciudadano.domain.entity.Solicitud;
import com.ciudadania360.subsistemaciudadano.domain.handler.InteraccionHandler;
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
class InteraccionServicioTest {

    @Mock
    private InteraccionHandler handler;

    @InjectMocks
    private InteraccionServicio svc;

    private Interaccion buildInteraccion() {
        Ciudadano ciudadano = Ciudadano.builder()
                .id(UUID.randomUUID())
                .nombre("Juan")
                .apellidos("Pérez")
                .email("juan@example.com")
                .build();

        Solicitud solicitud = Solicitud.builder()
                .id(UUID.randomUUID())
                .build();

        return Interaccion.builder()
                .id(UUID.randomUUID())
                .ciudadano(ciudadano)
                .solicitud(solicitud)
                .canal("email")
                .fecha(Instant.now())
                .agente("Agente1")
                .mensaje("Mensaje de prueba")
                .adjuntoUri(null)
                .visibilidad("PUBLICO")
                .version(1L)
                .build();
    }

    @Test
    void obtenerTodosDelegatesToHandler() {
        Interaccion e = buildInteraccion();
        when(handler.obtenerTodos()).thenReturn(List.of(e));

        List<Interaccion> result = svc.obtenerTodos();

        assertThat(result).containsExactly(e);
        verify(handler).obtenerTodos();
    }

    @Test
    void obtenerPorIdDelegatesToHandler() {
        Interaccion e = buildInteraccion();
        when(handler.obtenerPorId(e.getId())).thenReturn(e);

        Interaccion result = svc.obtenerPorId(e.getId());

        assertThat(result).isEqualTo(e);
        verify(handler).obtenerPorId(e.getId());
    }

    @Test
    void crearDelegatesToHandler() {
        Interaccion e = buildInteraccion();
        when(handler.crear(any())).thenReturn(e);

        Interaccion result = svc.crear(e);

        assertThat(result).isEqualTo(e);
        verify(handler).crear(e);
    }

    @Test
    void actualizarDelegatesToHandler() {
        Interaccion e = buildInteraccion();
        when(handler.actualizar(eq(e.getId()), any())).thenReturn(e);

        Interaccion result = svc.actualizar(e.getId(), e);

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
