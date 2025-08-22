package com.ciudadania360.subsistemacomunicaciones.application.service;

import com.ciudadania360.subsistemacomunicaciones.application.dto.notificacion.NotificacionRequest;
import com.ciudadania360.subsistemacomunicaciones.application.dto.notificacion.NotificacionResponse;
import com.ciudadania360.subsistemacomunicaciones.application.mapper.NotificacionMapper;
import com.ciudadania360.subsistemacomunicaciones.domain.entity.Notificacion;
import com.ciudadania360.subsistemacomunicaciones.domain.handler.NotificacionHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class NotificacionServiceTest {

    @Mock
    private NotificacionHandler handler;

    @Mock
    private NotificacionMapper mapper;

    @InjectMocks
    private NotificacionService svc;

    private Notificacion buildNotificacion() {
        return Notificacion.builder()
                .id(UUID.randomUUID())
                .ciudadanoId(UUID.randomUUID())
                .titulo("Título prueba")
                .mensaje("Mensaje de prueba")
                .canal("Email")
                .prioridad("ALTA")
                .estado("PENDIENTE")
                .referencia("REF123")
                .fechaEntrega(Instant.now())
                .version(0L)
                .build();
    }

    private NotificacionRequest toRequest(Notificacion e) {
        return new NotificacionRequest(
                e.getCiudadanoId(), e.getTitulo(), e.getMensaje(), e.getCanal(),
                e.getPrioridad(), e.getEstado(), e.getReferencia(), e.getFechaEntrega()
        );
    }

    private NotificacionResponse toResponse(Notificacion e) {
        return new NotificacionResponse(
                e.getId(), e.getCiudadanoId(), e.getTitulo(), e.getMensaje(),
                e.getCanal(), e.getPrioridad(), e.getEstado(), e.getReferencia(),
                e.getFechaEntrega()
        );
    }

    @Test
    void listDelegatesToHandler() {
        Notificacion e = buildNotificacion();
        when(handler.list()).thenReturn(List.of(e));
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        List<NotificacionResponse> result = svc.list();

        assertThat(result).containsExactly(toResponse(e));
        verify(handler).list();
        verify(mapper).toResponse(e);
    }

    @Test
    void getDelegatesToHandler() {
        Notificacion e = buildNotificacion();
        when(handler.get(e.getId())).thenReturn(e);
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        NotificacionResponse result = svc.get(e.getId());

        assertThat(result).isEqualTo(toResponse(e));
        verify(handler).get(e.getId());
        verify(mapper).toResponse(e);
    }

    @Test
    void createDelegatesToHandler() {
        Notificacion e = buildNotificacion();
        NotificacionRequest request = toRequest(e);
        NotificacionResponse expected = toResponse(e);

        when(mapper.toEntity(request)).thenReturn(e);
        when(handler.create(e)).thenReturn(e);
        when(mapper.toResponse(e)).thenReturn(expected);

        NotificacionResponse result = svc.create(request);

        assertThat(result).isEqualTo(expected);
        verify(mapper).toEntity(request);
        verify(handler).create(e);
        verify(mapper).toResponse(e);
    }

    @Test
    void updateDelegatesToHandler() {
        Notificacion e = buildNotificacion();
        NotificacionRequest request = toRequest(e);

        when(handler.get(e.getId())).thenReturn(e);
        doNothing().when(mapper).updateEntity(e, request);
        when(handler.update(e.getId(), e)).thenReturn(e);
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        NotificacionResponse result = svc.update(e.getId(), request);

        assertThat(result).isEqualTo(toResponse(e));
        verify(handler).get(e.getId());
        verify(mapper).updateEntity(e, request);
        verify(handler).update(e.getId(), e);
        verify(mapper).toResponse(e);
    }

    @Test
    void deleteDelegatesToHandler() {
        UUID id = UUID.randomUUID();

        svc.delete(id);

        verify(handler).delete(id);
        verifyNoInteractions(mapper);
    }
}
