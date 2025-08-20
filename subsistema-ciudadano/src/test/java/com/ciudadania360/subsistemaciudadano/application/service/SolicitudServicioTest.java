package com.ciudadania360.subsistemaciudadano.application.service;

import com.ciudadania360.subsistemaciudadano.domain.entity.Solicitud;
import com.ciudadania360.subsistemaciudadano.domain.handler.SolicitudHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SolicitudServicioTest {

    @Mock
    private SolicitudHandler handler;

    @InjectMocks
    private SolicitudServicio svc;

    private Solicitud buildSolicitud() {
        return Solicitud.builder()
                .id(UUID.randomUUID())
                .titulo("Título de prueba")
                .descripcion("Descripción de prueba")
                .tipo("TipoA")
                .canalEntrada("Web")
                .estado("Abierta")
                .prioridad("Alta")
                .numeroExpediente("EXP123")
                .fechaRegistro(Instant.now())
                .fechaLimiteSLA(Instant.now().plusSeconds(3600))
                .fechaCierre(null)
                .scoreRelevancia(BigDecimal.valueOf(0.9))
                .origen("Interno")
                .adjuntosCount(0)
                .encuestaEnviada(false)
                .referenciaExterna("REF001")
                .metadata("{}")
                .version(1L)
                .build();
    }

    @Test
    void obtenerTodosDelegatesToHandler() {
        Solicitud s = buildSolicitud();
        when(handler.obtenerTodos()).thenReturn(List.of(s));

        List<Solicitud> result = svc.obtenerTodos();

        assertThat(result).containsExactly(s);
        verify(handler).obtenerTodos();
    }

    @Test
    void obtenerPorIdDelegatesToHandler() {
        Solicitud s = buildSolicitud();
        when(handler.obtenerPorId(s.getId())).thenReturn(s);

        Solicitud result = svc.obtenerPorId(s.getId());

        assertThat(result).isEqualTo(s);
        verify(handler).obtenerPorId(s.getId());
    }

    @Test
    void crearDelegatesToHandler() {
        Solicitud s = buildSolicitud();
        when(handler.crear(any())).thenReturn(s);

        Solicitud result = svc.crear(s);

        assertThat(result).isEqualTo(s);
        verify(handler).crear(s);
    }

    @Test
    void actualizarDelegatesToHandler() {
        Solicitud s = buildSolicitud();
        when(handler.actualizar(eq(s.getId()), any())).thenReturn(s);

        Solicitud result = svc.actualizar(s.getId(), s);

        assertThat(result).isEqualTo(s);
        verify(handler).actualizar(s.getId(), s);
    }

    @Test
    void eliminarDelegatesToHandler() {
        UUID id = UUID.randomUUID();

        svc.eliminar(id);

        verify(handler).eliminar(id);
    }
}
