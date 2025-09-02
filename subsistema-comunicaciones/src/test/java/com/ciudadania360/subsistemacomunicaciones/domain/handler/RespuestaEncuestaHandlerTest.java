package com.ciudadania360.subsistemacomunicaciones.domain.handler;

import com.ciudadania360.subsistemacomunicaciones.domain.entity.RespuestaEncuesta;
import com.ciudadania360.subsistemacomunicaciones.domain.repository.RespuestaEncuestaRepository;
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
class RespuestaEncuestaHandlerTest {

    @Mock
    private RespuestaEncuestaRepository repo;

    @InjectMocks
    private RespuestaEncuestaHandler handler;

    @Test
    void listReturnsAllRespuestas() {
        RespuestaEncuesta e = RespuestaEncuesta.builder()
                .id(UUID.randomUUID())
                .encuestaId(UUID.randomUUID())
                .ciudadanoId(UUID.randomUUID())
                .respuestas("{\"q1\":\"Sí\"}")
                .puntuacion(5)
                .comentarios("Muy buena encuesta")
                .fecha(Instant.now())
                .version(0L)
                .build();

        when(repo.findAll()).thenReturn(List.of(e));

        List<RespuestaEncuesta> result = handler.list();

        assertThat(result).containsExactly(e);
        verify(repo).findAll();
    }

    @Test
    void getReturnsRespuestaById() {
        UUID id = UUID.randomUUID();
        RespuestaEncuesta e = RespuestaEncuesta.builder().id(id).respuestas("{\"q1\":\"No\"}").build();
        when(repo.findById(id)).thenReturn(Optional.of(e));

        RespuestaEncuesta result = handler.get(id);

        assertThat(result).isEqualTo(e);
        verify(repo).findById(id);
    }

    @Test
    void createSavesRespuesta() {
        RespuestaEncuesta e = RespuestaEncuesta.builder()
                .id(UUID.randomUUID())
                .respuestas("{\"q1\":\"Sí\"}")
                .build();
        when(repo.save(any())).thenReturn(e);

        RespuestaEncuesta result = handler.create(e);

        assertThat(result).isEqualTo(e);
        verify(repo).save(e);
    }

    @Test
    void updateSavesExistingRespuesta() {
        UUID id = UUID.randomUUID();
        RespuestaEncuesta e = RespuestaEncuesta.builder().id(id).respuestas("{\"q1\":\"Tal vez\"}").build();
        when(repo.findById(id)).thenReturn(Optional.of(e));
        when(repo.save(any())).thenReturn(e);

        RespuestaEncuesta result = handler.update(id, e);

        assertThat(result).isEqualTo(e);
        verify(repo).save(e);
    }

    @Test
    void deleteRemovesRespuestaById() {
        UUID id = UUID.randomUUID();

        handler.delete(id);

        verify(repo).deleteById(id);
    }
}
