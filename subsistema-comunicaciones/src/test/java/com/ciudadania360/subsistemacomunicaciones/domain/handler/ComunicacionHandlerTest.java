package com.ciudadania360.subsistemacomunicaciones.domain.handler;

import com.ciudadania360.subsistemacomunicaciones.domain.entity.Comunicacion;
import com.ciudadania360.subsistemacomunicaciones.domain.repository.ComunicacionRepositorio;
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
class ComunicacionHandlerTest {

    @Mock
    private ComunicacionRepositorio repo;

    @InjectMocks
    private ComunicacionHandler handler;

    @Test
    void listReturnsAllComunicaciones() {
        Comunicacion e = Comunicacion.builder()
                .id(UUID.randomUUID())
                .canal("Email")
                .asunto("Asunto de prueba")
                .cuerpo("Cuerpo de prueba")
                .ciudadanoId(UUID.randomUUID())
                .estado("Pendiente")
                .destinatario("juan@example.com")
                .programadaPara(Instant.now())
                .version(0L)
                .build();

        when(repo.findAll()).thenReturn(List.of(e));

        List<Comunicacion> result = handler.list();

        assertThat(result).containsExactly(e);
        verify(repo).findAll();
    }

    @Test
    void getReturnsComunicacionById() {
        UUID id = UUID.randomUUID();
        Comunicacion e = Comunicacion.builder().id(id).asunto("Asunto").build();
        when(repo.findById(id)).thenReturn(Optional.of(e));

        Comunicacion result = handler.get(id);

        assertThat(result).isEqualTo(e);
        verify(repo).findById(id);
    }

    @Test
    void createSavesComunicacion() {
        Comunicacion e = Comunicacion.builder().id(UUID.randomUUID()).asunto("Nuevo asunto").build();
        when(repo.save(any())).thenReturn(e);

        Comunicacion result = handler.create(e);

        assertThat(result).isEqualTo(e);
        verify(repo).save(e);
    }

    @Test
    void updateSavesExistingComunicacion() {
        UUID id = UUID.randomUUID();
        Comunicacion e = Comunicacion.builder().id(id).asunto("Asunto original").build();
        when(repo.findById(id)).thenReturn(Optional.of(e));
        when(repo.save(any())).thenReturn(e);

        Comunicacion result = handler.update(id, e);

        assertThat(result).isEqualTo(e);
        verify(repo).save(e);
    }

    @Test
    void deleteRemovesComunicacionById() {
        UUID id = UUID.randomUUID();

        handler.delete(id);

        verify(repo).deleteById(id);
    }
}
