package com.ciudadania360.subsistemaciudadano.domain.handler;

import com.ciudadania360.subsistemaciudadano.domain.entity.Notificacion;
import com.ciudadania360.subsistemaciudadano.domain.repository.NotificacionRepository;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificacionHandlerTest {

    @Mock
    private NotificacionRepository repo;

    @InjectMocks
    private NotificacionHandler handler;

    private Notificacion buildNotificacion() {
        return Notificacion.builder()
                .id(UUID.randomUUID())
                .canal("Email")
                .estado("ENVIADO")
                .fechaEnvio(Instant.now())
                .mensaje("Mensaje de prueba")
                .metadata("{\"detalle\":\"test\"}")
                .build();
    }

    @Test
    void listReturnsAllNotificaciones() {
        Notificacion n = buildNotificacion();
        when(repo.findAll()).thenReturn(List.of(n));

        List<Notificacion> result = handler.list();

        assertThat(result).containsExactly(n);
        verify(repo).findAll();
    }

    @Test
    void getReturnsNotificacionById() {
        Notificacion n = buildNotificacion();
        when(repo.findById(n.getId())).thenReturn(Optional.of(n));

        Notificacion result = handler.get(n.getId());

        assertThat(result).isEqualTo(n);
        verify(repo).findById(n.getId());
    }

    @Test
    void createSavesNotificacion() {
        Notificacion n = buildNotificacion();
        when(repo.save(any())).thenReturn(n);

        Notificacion result = handler.create(n);

        assertThat(result).isEqualTo(n);
        verify(repo).save(n);
    }

    @Test
    void updateSavesExistingNotificacion() {
        Notificacion n = buildNotificacion();
        when(repo.findById(n.getId())).thenReturn(Optional.of(n));
        when(repo.save(any())).thenReturn(n);

        Notificacion result = handler.update(n.getId(), n);

        assertThat(result).isEqualTo(n);
        verify(repo).save(n);
    }

    @Test
    void deleteRemovesNotificacionById() {
        UUID id = UUID.randomUUID();

        handler.delete(id);

        verify(repo).deleteById(id);
    }
}
