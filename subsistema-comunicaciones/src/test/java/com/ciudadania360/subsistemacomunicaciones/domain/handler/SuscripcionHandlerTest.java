package com.ciudadania360.subsistemacomunicaciones.domain.handler;

import com.ciudadania360.subsistemacomunicaciones.domain.entity.Suscripcion;
import com.ciudadania360.subsistemacomunicaciones.domain.repository.SuscripcionRepositorio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SuscripcionHandlerTest {

    @Mock
    private SuscripcionRepositorio repo;

    @InjectMocks
    private SuscripcionHandler handler;

    @Test
    void listReturnsAllSuscripciones() {
        Suscripcion e = Suscripcion.builder()
                .id(UUID.randomUUID())
                .ciudadanoId(UUID.randomUUID())
                .tema("Noticias")
                .activo(true)
                .version(0L)
                .build();

        when(repo.findAll()).thenReturn(List.of(e));

        List<Suscripcion> result = handler.list();

        assertThat(result).containsExactly(e);
        verify(repo).findAll();
    }

    @Test
    void getReturnsSuscripcionById() {
        UUID id = UUID.randomUUID();
        Suscripcion e = Suscripcion.builder().id(id).tema("Eventos").activo(true).build();
        when(repo.findById(id)).thenReturn(Optional.of(e));

        Suscripcion result = handler.get(id);

        assertThat(result).isEqualTo(e);
        verify(repo).findById(id);
    }

    @Test
    void createSavesSuscripcion() {
        Suscripcion e = Suscripcion.builder()
                .id(UUID.randomUUID())
                .tema("Promociones")
                .activo(true)
                .build();
        when(repo.save(any())).thenReturn(e);

        Suscripcion result = handler.create(e);

        assertThat(result).isEqualTo(e);
        verify(repo).save(e);
    }

    @Test
    void updateSavesExistingSuscripcion() {
        UUID id = UUID.randomUUID();
        Suscripcion e = Suscripcion.builder().id(id).tema("Alertas").activo(false).build();
        when(repo.findById(id)).thenReturn(Optional.of(e));
        when(repo.save(any())).thenReturn(e);

        Suscripcion result = handler.update(id, e);

        assertThat(result).isEqualTo(e);
        verify(repo).save(e);
    }

    @Test
    void deleteRemovesSuscripcionById() {
        UUID id = UUID.randomUUID();

        handler.delete(id);

        verify(repo).deleteById(id);
    }
}
