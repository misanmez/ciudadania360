package com.ciudadania360.subsistemacomunicaciones.domain.handler;

import com.ciudadania360.subsistemacomunicaciones.domain.entity.Encuesta;
import com.ciudadania360.subsistemacomunicaciones.domain.repository.EncuestaRepository;
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
class EncuestaHandlerTest {

    @Mock
    private EncuestaRepository repo;

    @InjectMocks
    private EncuestaHandler handler;

    @Test
    void listReturnsAllEncuestas() {
        Encuesta e = Encuesta.builder()
                .id(UUID.randomUUID())
                .titulo("Encuesta de Satisfacción")
                .descripcion("Descripción de la encuesta")
                .preguntas("[]")
                .estado("Activo")
                .audiencia("General")
                .fechaInicio(Instant.now())
                .fechaFin(Instant.now().plusSeconds(3600))
                .vinculadaSolicitudId(UUID.randomUUID())
                .version(0L)
                .build();

        when(repo.findAll()).thenReturn(List.of(e));

        List<Encuesta> result = handler.list();

        assertThat(result).containsExactly(e);
        verify(repo).findAll();
    }

    @Test
    void getReturnsEncuestaById() {
        UUID id = UUID.randomUUID();
        Encuesta e = Encuesta.builder().id(id).titulo("Encuesta de prueba").build();
        when(repo.findById(id)).thenReturn(Optional.of(e));

        Encuesta result = handler.get(id);

        assertThat(result).isEqualTo(e);
        verify(repo).findById(id);
    }

    @Test
    void createSavesEncuesta() {
        Encuesta e = Encuesta.builder().id(UUID.randomUUID()).titulo("Nueva encuesta").build();
        when(repo.save(any())).thenReturn(e);

        Encuesta result = handler.create(e);

        assertThat(result).isEqualTo(e);
        verify(repo).save(e);
    }

    @Test
    void updateSavesExistingEncuesta() {
        UUID id = UUID.randomUUID();
        Encuesta e = Encuesta.builder().id(id).titulo("Encuesta original").build();
        when(repo.findById(id)).thenReturn(Optional.of(e));
        when(repo.save(any())).thenReturn(e);

        Encuesta result = handler.update(id, e);

        assertThat(result).isEqualTo(e);
        verify(repo).save(e);
    }

    @Test
    void deleteRemovesEncuestaById() {
        UUID id = UUID.randomUUID();

        handler.delete(id);

        verify(repo).deleteById(id);
    }
}
