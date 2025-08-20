package com.ciudadania360.subsistemainformacion.application.service;

import com.ciudadania360.subsistemainformacion.domain.entity.Dataset;
import com.ciudadania360.subsistemainformacion.domain.handler.DatasetHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DatasetServicioTest {

    @Mock
    private DatasetHandler handler;

    @InjectMocks
    private DatasetServicio svc;

    private Dataset buildDataset() {
        return Dataset.builder()
                .id(UUID.randomUUID())
                .nombre("Example Dataset")
                .descripcion("Description of dataset")
                .fuente("http://example.com/dataset")
                .esquema("{\"field1\":\"string\"}")
                .periodicidad("Mensual")
                .licencia("CC BY 4.0")
                .urlPortalDatos("http://example.com/dataset")
                .formato("CSV")
                .responsable("Departamento de Datos")
                .ultimaActualizacion(Instant.now())
                .version(1L)
                .build();
    }

    @Test
    void listDelegatesToHandler() {
        Dataset d = buildDataset();
        when(handler.list()).thenReturn(List.of(d));

        List<Dataset> result = svc.list();

        assertThat(result).containsExactly(d);
        verify(handler).list();
    }

    @Test
    void getDelegatesToHandler() {
        Dataset d = buildDataset();
        when(handler.get(d.getId())).thenReturn(d);

        Dataset result = svc.get(d.getId());

        assertThat(result).isEqualTo(d);
        verify(handler).get(d.getId());
    }

    @Test
    void createDelegatesToHandler() {
        Dataset d = buildDataset();
        when(handler.create(any())).thenReturn(d);

        Dataset result = svc.create(d);

        assertThat(result).isEqualTo(d);
        verify(handler).create(d);
    }

    @Test
    void updateDelegatesToHandler() {
        Dataset d = buildDataset();
        when(handler.update(eq(d.getId()), any())).thenReturn(d);

        Dataset result = svc.update(d.getId(), d);

        assertThat(result).isEqualTo(d);
        verify(handler).update(d.getId(), d);
    }

    @Test
    void deleteDelegatesToHandler() {
        UUID id = UUID.randomUUID();

        svc.delete(id);

        verify(handler).delete(id);
    }
}
