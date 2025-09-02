package com.ciudadania360.subsistemainformacion.domain.handler;

import com.ciudadania360.subsistemainformacion.domain.entity.Recomendacion;
import com.ciudadania360.subsistemainformacion.domain.repository.RecomendacionRepository;
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
class RecomendacionHandlerTest {

    @Mock
    private RecomendacionRepository repo;

    @InjectMocks
    private RecomendacionHandler handler;

    @Test
    void listReturnsAllRecomendaciones() {
        Recomendacion e = Recomendacion.builder()
                .id(UUID.randomUUID())
                .titulo("Recomendación de Ejemplo")
                .detalle("Descripción de la recomendación")
                .build();

        when(repo.findAll()).thenReturn(List.of(e));

        List<Recomendacion> result = handler.list();

        assertThat(result).containsExactly(e);
        verify(repo).findAll();
    }

    @Test
    void getReturnsRecomendacionById() {
        UUID id = UUID.randomUUID();
        Recomendacion e = Recomendacion.builder()
                .id(id)
                .titulo("Ejemplo")
                .detalle("Detalle ejemplo")
                .build();
        when(repo.findById(id)).thenReturn(Optional.of(e));

        Recomendacion result = handler.get(id);

        assertThat(result).isEqualTo(e);
        verify(repo).findById(id);
    }

    @Test
    void createSavesRecomendacion() {
        Recomendacion e = Recomendacion.builder()
                .id(UUID.randomUUID())
                .titulo("Nueva recomendación")
                .detalle("Detalle nuevo")
                .build();
        when(repo.save(any())).thenReturn(e);

        Recomendacion result = handler.create(e);

        assertThat(result).isEqualTo(e);
        verify(repo).save(e);
    }

    @Test
    void updateSavesExistingRecomendacion() {
        UUID id = UUID.randomUUID();
        Recomendacion e = Recomendacion.builder()
                .id(id)
                .titulo("Original")
                .detalle("Detalle original")
                .build();
        when(repo.findById(id)).thenReturn(Optional.of(e));
        when(repo.save(any())).thenReturn(e);

        Recomendacion result = handler.update(id, e);

        assertThat(result).isEqualTo(e);
        verify(repo).save(e);
    }

    @Test
    void deleteRemovesRecomendacionById() {
        UUID id = UUID.randomUUID();

        handler.delete(id);

        verify(repo).deleteById(id);
    }
}
