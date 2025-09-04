package com.ciudadania360.subsistemaciudadano.application.service;

import com.ciudadania360.subsistemaciudadano.application.dto.notificacion.NotificacionRequest;
import com.ciudadania360.subsistemaciudadano.application.dto.notificacion.NotificacionResponse;
import com.ciudadania360.subsistemaciudadano.application.mapper.NotificacionMapper;
import com.ciudadania360.subsistemaciudadano.application.validator.NotificacionValidator;
import com.ciudadania360.subsistemaciudadano.domain.entity.Notificacion;
import com.ciudadania360.subsistemaciudadano.domain.entity.Solicitud;
import com.ciudadania360.subsistemaciudadano.domain.handler.NotificacionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class NotificacionServiceTest {

    @Mock private NotificacionHandler handler;
    @Mock private NotificacionMapper mapper;
    @Mock private NotificacionValidator validator;

    @InjectMocks private NotificacionService svc;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    private Notificacion buildNotificacion() {
        return Notificacion.builder()
                .id(UUID.randomUUID())
                .solicitud(Solicitud.builder().id(UUID.randomUUID()).build())
                .canal("Email")
                .fechaEnvio(Instant.now())
                .estado("PENDIENTE")
                .mensaje("Mensaje de prueba")
                .metadata("{\"nota\":\"test\"}")
                .version(1L)
                .build();
    }

    private NotificacionRequest toRequest(Notificacion n) {
        return new NotificacionRequest(
                n.getSolicitud() != null ? n.getSolicitud().getId() : null,
                n.getCanal(),
                n.getFechaEnvio(),
                n.getEstado(),
                n.getMensaje(),
                n.getMetadata()
        );
    }

    private NotificacionResponse toResponse(Notificacion n) {
        return new NotificacionResponse(
                n.getId(),
                n.getSolicitud() != null ? n.getSolicitud().getId() : null,
                n.getCanal(),
                n.getFechaEnvio(),
                n.getEstado(),
                n.getMensaje(),
                n.getMetadata()
        );
    }

    @Test void listDelegatesToHandler() {
        Notificacion n = buildNotificacion();
        when(handler.list()).thenReturn(List.of(n));
        when(mapper.toResponse(n)).thenReturn(toResponse(n));

        List<NotificacionResponse> result = svc.list();

        assertThat(result).containsExactly(toResponse(n));
        verify(handler).list();
        verify(mapper).toResponse(n);
    }

    @Test void getDelegatesToHandler() {
        Notificacion n = buildNotificacion();
        when(handler.get(n.getId())).thenReturn(n);
        when(mapper.toResponse(n)).thenReturn(toResponse(n));

        NotificacionResponse result = svc.get(n.getId());

        assertThat(result).isEqualTo(toResponse(n));
        verify(handler).get(n.getId());
        verify(mapper).toResponse(n);
    }

    @Test void createDelegatesToHandler() {
        Notificacion n = buildNotificacion();
        NotificacionRequest request = toRequest(n);
        NotificacionResponse expectedResponse = toResponse(n);

        when(mapper.toEntity(request)).thenReturn(n);
        when(handler.create(n)).thenReturn(n);
        when(mapper.toResponse(n)).thenReturn(expectedResponse);

        NotificacionResponse result = svc.create(request);

        assertThat(result).isEqualTo(expectedResponse);
        verify(validator).validateCreate(request);
        verify(mapper).toEntity(request);
        verify(handler).create(n);
        verify(mapper).toResponse(n);
    }

    @Test void updateDelegatesToHandler() {
        Notificacion n = buildNotificacion();
        NotificacionRequest request = toRequest(n);
        NotificacionResponse expectedResponse = toResponse(n);

        when(handler.get(n.getId())).thenReturn(n);
        doNothing().when(mapper).updateEntity(n, request);
        when(handler.update(n.getId(), n)).thenReturn(n);
        when(mapper.toResponse(n)).thenReturn(expectedResponse);

        NotificacionResponse result = svc.update(n.getId(), request);

        assertThat(result).isEqualTo(expectedResponse);
        verify(validator).validateUpdate(request);
        verify(handler).get(n.getId());
        verify(mapper).updateEntity(n, request);
        verify(handler).update(n.getId(), n);
        verify(mapper).toResponse(n);
    }

    @Test void deleteDelegatesToHandler() {
        UUID id = UUID.randomUUID();
        when(handler.exists(id)).thenReturn(true);

        svc.delete(id);

        verify(handler).exists(id);
        verify(validator).validateDelete(id, true);
        verify(handler).delete(id);
        verifyNoInteractions(mapper);
    }
}
