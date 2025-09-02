package com.ciudadania360.subsistemacomunicaciones.domain.handler;

import com.ciudadania360.subsistemacomunicaciones.domain.entity.Notificacion;
import com.ciudadania360.subsistemacomunicaciones.domain.repository.NotificacionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificacionHandlerTest {

    @Mock
    private NotificacionRepository repo;

    @InjectMocks
    private NotificacionHandler handler;

    @Test
    void listReturnsAllNotificaciones() {
        Notificacion e = Notificacion.builder()
                .id(UUID.randomUUID())
                .titulo("Notificación de Prueba")
                .mensaje("Contenido de la notificación")
                .canal("Email")
                .prioridad("Alta")
                .estado("Pendiente")
                .referencia("REF123")
                .ciudadanoId(UUID.randomUUID())
                .fechaEntrega(Instant.now())
                .version(0L)
                .build();

        when(repo.findAll()).thenReturn(List.of(e));

        List<Notificacion> result = handler.list();

        assertThat(result).containsExactly(e);
        verify(repo).findAll();
    }

    @Test
    void getReturnsNotificacionById() {
        UUID id = UUID.randomUUID();
        Notificacion e = Notificacion.builder().id(id).titulo("Notificación de prueba").build();
        when(repo.findById(id)).thenReturn(Optional.of(e));

        Notificacion result = handler.get(id);

        assertThat(result).isEqualTo(e);
        verify(repo).findById(id);
    }

    @Test
    void createSavesNotificacion() {
        Notificacion e = Notificacion.builder().id(UUID.randomUUID()).titulo("Nueva Notificación").build();
        when(repo.save(any())).thenReturn(e);

        Notificacion result = handler.create(e);

        assertThat(result).isEqualTo(e);
        verify(repo).save(e);
    }

    @Test
    void updateSavesExistingNotificacion() {
        UUID id = UUID.randomUUID();
        Notificacion e = Notificacion.builder().id(id).titulo("Notificación original").build();
        when(repo.findById(id)).thenReturn(Optional.of(e));
        when(repo.save(any())).thenReturn(e);

        Notificacion result = handler.update(id, e);

        assertThat(result).isEqualTo(e);
        verify(repo).save(e);
    }

    @Test
    void deleteRemovesNotificacionById() {
        UUID id = UUID.randomUUID();

        handler.delete(id);

        verify(repo).deleteById(id);
    }
}
