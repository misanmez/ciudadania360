package com.ciudadania360.subsistemainformacion.domain.handler;

import com.ciudadania360.subsistemainformacion.domain.entity.Dataset;
import com.ciudadania360.subsistemainformacion.domain.repository.DatasetRepository;
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
class DatasetHandlerTest {

    @Mock
    private DatasetRepository repo;

    @InjectMocks
    private DatasetHandler handler;

    @Test
    void listReturnsAllDatasets() {
        Dataset e = Dataset.builder()
                .id(UUID.randomUUID())
                .nombre("Example Dataset")
                .descripcion("Description of dataset")
                .fuente("Fuente ejemplo")
                .urlPortalDatos("http://example.com/dataset")
                .ultimaActualizacion(Instant.now())
                .version(0L)
                .build();

        when(repo.findAll()).thenReturn(List.of(e));

        List<Dataset> result = handler.list();

        assertThat(result).containsExactly(e);
        verify(repo).findAll();
    }

    @Test
    void getReturnsDatasetById() {
        UUID id = UUID.randomUUID();
        Dataset e = Dataset.builder().id(id).nombre("Example Dataset").build();
        when(repo.findById(id)).thenReturn(Optional.of(e));

        Dataset result = handler.get(id);

        assertThat(result).isEqualTo(e);
        verify(repo).findById(id);
    }

    @Test
    void createSavesDataset() {
        Dataset e = Dataset.builder()
                .id(UUID.randomUUID())
                .nombre("New Dataset")
                .descripcion("New description")
                .build();
        when(repo.save(any())).thenReturn(e);

        Dataset result = handler.create(e);

        assertThat(result).isEqualTo(e);
        verify(repo).save(e);
    }

    @Test
    void updateSavesExistingDataset() {
        UUID id = UUID.randomUUID();
        Dataset e = Dataset.builder().id(id).nombre("Original Dataset").build();
        when(repo.findById(id)).thenReturn(Optional.of(e));
        when(repo.save(any())).thenReturn(e);

        Dataset result = handler.update(id, e);

        assertThat(result).isEqualTo(e);
        verify(repo).save(e);
    }

    @Test
    void deleteRemovesDatasetById() {
        UUID id = UUID.randomUUID();

        handler.delete(id);

        verify(repo).deleteById(id);
    }
}
