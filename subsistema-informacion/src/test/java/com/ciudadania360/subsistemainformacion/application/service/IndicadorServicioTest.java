package com.ciudadania360.subsistemainformacion.application.service;

import com.ciudadania360.subsistemainformacion.domain.entity.Indicador;
import com.ciudadania360.subsistemainformacion.domain.handler.IndicadorHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IndicadorServicioTest {

    @Mock
    private IndicadorHandler handler;

    @InjectMocks
    private IndicadorServicio svc;

    private Indicador buildIndicador() {
        return Indicador.builder()
                .id(UUID.randomUUID())
                .codigo("IND-001")
                .nombre("Indicador de Ejemplo")
                .descripcion("Descripción del indicador")
                .definicionCalculo("Definición de cálculo ejemplo")
                .frecuencia("Mensual")
                .unidad("Porcentaje")
                .responsable("Departamento Ejemplo")
                .kpi(true)
                .origenDatos("Fuente interna")
                .datasetId(UUID.randomUUID())
                .version(1L)
                .build();
    }

    @Test
    void listDelegatesToHandler() {
        Indicador i = buildIndicador();
        when(handler.list()).thenReturn(List.of(i));

        List<Indicador> result = svc.list();

        assertThat(result).containsExactly(i);
        verify(handler).list();
    }

    @Test
    void getDelegatesToHandler() {
        Indicador i = buildIndicador();
        when(handler.get(i.getId())).thenReturn(i);

        Indicador result = svc.get(i.getId());

        assertThat(result).isEqualTo(i);
        verify(handler).get(i.getId());
    }

    @Test
    void createDelegatesToHandler() {
        Indicador i = buildIndicador();
        when(handler.create(any())).thenReturn(i);

        Indicador result = svc.create(i);

        assertThat(result).isEqualTo(i);
        verify(handler).create(i);
    }

    @Test
    void updateDelegatesToHandler() {
        Indicador i = buildIndicador();
        when(handler.update(eq(i.getId()), any())).thenReturn(i);

        Indicador result = svc.update(i.getId(), i);

        assertThat(result).isEqualTo(i);
        verify(handler).update(i.getId(), i);
    }

    @Test
    void deleteDelegatesToHandler() {
        UUID id = UUID.randomUUID();

        svc.delete(id);

        verify(handler).delete(id);
    }
}
