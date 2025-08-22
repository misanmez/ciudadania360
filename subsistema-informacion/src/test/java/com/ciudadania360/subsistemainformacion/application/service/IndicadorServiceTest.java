package com.ciudadania360.subsistemainformacion.application.service;

import com.ciudadania360.subsistemainformacion.application.dto.indicador.IndicadorRequest;
import com.ciudadania360.subsistemainformacion.application.dto.indicador.IndicadorResponse;
import com.ciudadania360.subsistemainformacion.application.mapper.IndicadorMapper;
import com.ciudadania360.subsistemainformacion.domain.entity.Indicador;
import com.ciudadania360.subsistemainformacion.domain.handler.IndicadorHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class IndicadorServiceTest {

    @Mock
    private IndicadorHandler handler;

    @Mock
    private IndicadorMapper mapper;

    @InjectMocks
    private IndicadorService svc;

    private Indicador buildEntity() {
        return Indicador.builder()
                .id(UUID.randomUUID())
                .codigo("IND001")
                .nombre("Indicador de prueba")
                .descripcion("Descripción")
                .definicionCalculo("Suma")
                .frecuencia("Mensual")
                .unidad("Unidad")
                .responsable("Admin")
                .kpi(true)
                .origenDatos("Sistema")
                .datasetId(UUID.randomUUID())
                .version(1L)
                .build();
    }

    private IndicadorRequest toRequest(Indicador e) {
        return new IndicadorRequest(e.getCodigo(), e.getNombre(), e.getDescripcion(),
                e.getDefinicionCalculo(), e.getFrecuencia(), e.getUnidad(),
                e.getResponsable(), e.getKpi(), e.getOrigenDatos(), e.getDatasetId());
    }

    private IndicadorResponse toResponse(Indicador e) {
        return new IndicadorResponse(e.getId(), e.getCodigo(), e.getNombre(), e.getDescripcion(),
                e.getDefinicionCalculo(), e.getFrecuencia(), e.getUnidad(),
                e.getResponsable(), e.getKpi(), e.getOrigenDatos(), e.getDatasetId());
    }

    @Test
    void listDelegatesToHandler() {
        Indicador e = buildEntity();
        when(handler.list()).thenReturn(List.of(e));
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        List<IndicadorResponse> result = svc.list();
        assertThat(result).containsExactly(toResponse(e));
        verify(handler).list();
        verify(mapper).toResponse(e);
    }

    @Test
    void getDelegatesToHandler() {
        Indicador e = buildEntity();
        when(handler.get(e.getId())).thenReturn(e);
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        IndicadorResponse result = svc.get(e.getId());
        assertThat(result).isEqualTo(toResponse(e));
        verify(handler).get(e.getId());
        verify(mapper).toResponse(e);
    }

    @Test
    void createDelegatesToHandler() {
        Indicador e = Indicador.builder()
                .id(UUID.randomUUID())
                .codigo("IND-001")
                .nombre("Indicador 1")
                .descripcion("Descripción")
                .definicionCalculo("Definición")
                .frecuencia("Mensual")
                .unidad("Unidad")
                .responsable("Responsable")
                .kpi(true)
                .origenDatos("Origen")
                .datasetId(UUID.randomUUID())
                .version(1L)
                .build();

        IndicadorRequest request = toRequest(e);

        when(mapper.toEntity(request)).thenReturn(e);
        when(handler.create(any(Indicador.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(mapper.toResponse(any(Indicador.class))).thenAnswer(invocation -> {
            Indicador d = invocation.getArgument(0);
            return toResponse(d);
        });

        IndicadorResponse result = svc.create(request);

        assertThat(result.getCodigo()).isEqualTo(e.getCodigo());
        assertThat(result.getNombre()).isEqualTo(e.getNombre());
        assertThat(result.getId()).isNotNull();

        verify(mapper).toEntity(request);
        verify(handler).create(any(Indicador.class));
        verify(mapper).toResponse(any(Indicador.class));
    }


    @Test
    void updateDelegatesToHandler() {
        Indicador e = buildEntity();
        IndicadorRequest request = toRequest(e);
        when(handler.get(e.getId())).thenReturn(e);
        doNothing().when(mapper).updateEntity(e, request);
        when(handler.update(e.getId(), e)).thenReturn(e);
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        IndicadorResponse result = svc.update(e.getId(), request);
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
