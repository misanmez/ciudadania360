package com.ciudadania360.subsistemacomunicaciones.application.service;

import com.ciudadania360.subsistemacomunicaciones.domain.entity.Notificacion;
import com.ciudadania360.subsistemacomunicaciones.domain.handler.NotificacionHandler;
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
class NotificacionServicioTest {

    @Mock
    private NotificacionHandler handler;

    @InjectMocks
    private NotificacionServicio svc;

    private Notificacion buildNotificacion() {
        return Notificacion.builder()
                .id(UUID.randomUUID())
                .ciudadanoId(UUID.randomUUID())
                .titulo("Notificación de Prueba")
                .mensaje("Contenido de la notificación")
                .canal("Email")
                .prioridad("Alta")
                .estado("PENDIENTE")
                .referencia("REF123")
                .fechaEntrega(Instant.now())
                .version(1L)
                .build();
    }

    @Test
    void listDelegatesToHandler() {
        Notificacion e = buildNotificacion();
        when(handler.list()).thenReturn(List.of(e));

        List<Notificacion> result = svc.list();

        assertThat(result).containsExactly(e);
        verify(handler).list();
    }

    @Test
    void getDelegatesToHandler() {
        Notificacion e = buildNotificacion();
        when(handler.get(e.getId())).thenReturn(e);

        Notificacion result = svc.get(e.getId());

        assertThat(result).isEqualTo(e);
        verify(handler).get(e.getId());
    }

    @Test
    void createDelegatesToHandler() {
        Notificacion e = buildNotificacion();
        when(handler.create(any())).thenReturn(e);

        Notificacion result = svc.create(e);

        assertThat(result).isEqualTo(e);
        verify(handler).create(e);
    }

    @Test
    void updateDelegatesToHandler() {
        Notificacion e = buildNotificacion();
        when(handler.update(eq(e.getId()), any())).thenReturn(e);

        Notificacion result = svc.update(e.getId(), e);

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
