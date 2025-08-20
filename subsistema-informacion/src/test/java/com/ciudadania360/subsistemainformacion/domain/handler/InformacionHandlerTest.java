package com.ciudadania360.subsistemainformacion.domain.handler;

import com.ciudadania360.subsistemainformacion.domain.entity.Informacion;
import com.ciudadania360.subsistemainformacion.domain.repository.InformacionRepositorio;
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
class InformacionHandlerTest {

    @Mock
    private InformacionRepositorio repo;

    @InjectMocks
    private InformacionHandler handler;

    @Test
    void listReturnsAllInformaciones() {
        Informacion e = Informacion.builder()
                .id(UUID.randomUUID())
                .titulo("Información de Ejemplo")
                .contenido("Descripción de la información")
                .fechaPublicacion(Instant.parse("2023-10-01T00:00:00Z"))
                .build();

        when(repo.findAll()).thenReturn(List.of(e));

        List<Informacion> result = handler.list();

        assertThat(result).containsExactly(e);
        verify(repo).findAll();
    }

    @Test
    void getReturnsInformacionById() {
        UUID id = UUID.randomUUID();
        Informacion e = Informacion.builder()
                .id(id)
                .titulo("Información ejemplo")
                .contenido("Contenido ejemplo")
                .fechaPublicacion(Instant.now())
                .build();
        when(repo.findById(id)).thenReturn(Optional.of(e));

        Informacion result = handler.get(id);

        assertThat(result).isEqualTo(e);
        verify(repo).findById(id);
    }

    @Test
    void createSavesInformacion() {
        Informacion e = Informacion.builder()
                .id(UUID.randomUUID())
                .titulo("Nueva información")
                .contenido("Nuevo contenido")
                .fechaPublicacion(Instant.now())
                .build();
        when(repo.save(any())).thenReturn(e);

        Informacion result = handler.create(e);

        assertThat(result).isEqualTo(e);
        verify(repo).save(e);
    }

    @Test
    void updateSavesExistingInformacion() {
        UUID id = UUID.randomUUID();
        Informacion e = Informacion.builder()
                .id(id)
                .titulo("Información original")
                .contenido("Contenido original")
                .fechaPublicacion(Instant.now())
                .build();
        when(repo.findById(id)).thenReturn(Optional.of(e));
        when(repo.save(any())).thenReturn(e);

        Informacion result = handler.update(id, e);

        assertThat(result).isEqualTo(e);
        verify(repo).save(e);
    }

    @Test
    void deleteRemovesInformacionById() {
        UUID id = UUID.randomUUID();

        handler.delete(id);

        verify(repo).deleteById(id);
    }
}
