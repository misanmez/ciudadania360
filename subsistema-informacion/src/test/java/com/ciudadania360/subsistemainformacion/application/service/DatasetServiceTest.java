package com.ciudadania360.subsistemainformacion.application.service;

import com.ciudadania360.subsistemainformacion.application.dto.dataset.DatasetRequest;
import com.ciudadania360.subsistemainformacion.application.dto.dataset.DatasetResponse;
import com.ciudadania360.subsistemainformacion.application.mapper.DatasetMapper;
import com.ciudadania360.subsistemainformacion.domain.entity.Dataset;
import com.ciudadania360.subsistemainformacion.domain.handler.DatasetHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class DatasetServiceTest {

    @Mock
    private DatasetHandler handler;

    @Mock
    private DatasetMapper mapper;

    @InjectMocks
    private DatasetService svc;

    private Dataset buildEntity() {
        return Dataset.builder()
                .id(UUID.randomUUID())
                .nombre("Dataset 1")
                .descripcion("Descripción del dataset")
                .fuente("Fuente 1")
                .esquema("{\"campo\":\"tipo\"}")
                .periodicidad("Mensual")
                .licencia("MIT")
                .urlPortalDatos("http://datos.example.com")
                .formato("CSV")
                .responsable("Responsable")
                .ultimaActualizacion(Instant.now())
                .version(1L)
                .build();
    }

    private DatasetRequest toRequest(Dataset e) {
        return new DatasetRequest(
                e.getNombre(), e.getDescripcion(), e.getFuente(), e.getEsquema(),
                e.getPeriodicidad(), e.getLicencia(), e.getUrlPortalDatos(),
                e.getFormato(), e.getResponsable(), e.getUltimaActualizacion()
        );
    }

    private DatasetResponse toResponse(Dataset e) {
        return new DatasetResponse(
                e.getId(), e.getNombre(), e.getDescripcion(), e.getFuente(), e.getEsquema(),
                e.getPeriodicidad(), e.getLicencia(), e.getUrlPortalDatos(),
                e.getFormato(), e.getResponsable(), e.getUltimaActualizacion()
        );
    }

    @Test
    void listDelegatesToHandler() {
        Dataset e = buildEntity();
        when(handler.list()).thenReturn(List.of(e));
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        List<DatasetResponse> result = svc.list();

        assertThat(result).containsExactly(toResponse(e));
        verify(handler).list();
        verify(mapper).toResponse(e);
    }

    @Test
    void getDelegatesToHandler() {
        Dataset e = buildEntity();
        when(handler.get(e.getId())).thenReturn(e);
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        DatasetResponse result = svc.get(e.getId());

        assertThat(result).isEqualTo(toResponse(e));
        verify(handler).get(e.getId());
        verify(mapper).toResponse(e);
    }

    @Test
    void createDelegatesToHandler() {
        Dataset e = buildEntity();
        DatasetRequest request = toRequest(e);

        when(mapper.toEntity(request)).thenReturn(e);
        when(handler.create(any(Dataset.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(mapper.toResponse(any(Dataset.class))).thenAnswer(invocation -> {
            Dataset d = invocation.getArgument(0);
            return new DatasetResponse(
                    d.getId(),
                    d.getNombre(),
                    d.getDescripcion(),
                    d.getFuente(),
                    d.getEsquema(),
                    d.getPeriodicidad(),
                    d.getLicencia(),
                    d.getUrlPortalDatos(),
                    d.getFormato(),
                    d.getResponsable(),
                    d.getUltimaActualizacion()
            );
        });

        DatasetResponse result = svc.create(request);

        assertThat(result.getNombre()).isEqualTo(e.getNombre());
        assertThat(result.getDescripcion()).isEqualTo(e.getDescripcion());
        assertThat(result.getFuente()).isEqualTo(e.getFuente());
        assertThat(result.getId()).isNotNull();

        verify(mapper).toEntity(request);
        verify(handler).create(any(Dataset.class));
        verify(mapper).toResponse(any(Dataset.class));
    }


    @Test
    void updateDelegatesToHandler() {
        Dataset e = buildEntity();
        DatasetRequest request = toRequest(e);

        when(handler.get(e.getId())).thenReturn(e);
        doNothing().when(mapper).updateEntity(e, request);
        when(handler.update(eq(e.getId()), same(e))).thenReturn(e);
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        DatasetResponse result = svc.update(e.getId(), request);

        assertThat(result).isEqualTo(toResponse(e));
        verify(handler).get(e.getId());
        verify(mapper).updateEntity(e, request);
        verify(handler).update(e.getId(), e);
        verify(mapper).toResponse(e);
    }

    @Test
    void deleteDelegatesToHandler() {
        UUID id = UUID.randomUUID();
        svc.delete(id);
        verify(handler).delete(id);
        verifyNoInteractions(mapper);
    }
}
