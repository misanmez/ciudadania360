package com.ciudadania360.subsistematramitacion.application.service;

import com.ciudadania360.subsistematramitacion.domain.entity.IncidenciaContrata;
import com.ciudadania360.subsistematramitacion.domain.handler.IncidenciaContrataHandler;
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
class IncidenciaContrataServicioTest {

    @Mock
    private IncidenciaContrataHandler handler;

    @InjectMocks
    private IncidenciaContrataServicio svc;

    private IncidenciaContrata buildIncidencia() {
        return IncidenciaContrata.builder()
                .id(UUID.randomUUID())
                .contrataId(UUID.randomUUID())
                .descripcion("Descripción de la incidencia")
                .estado("Estado de la incidencia")
                .build();
    }

    @Test
    void listDelegatesToHandler() {
        IncidenciaContrata incidencia = buildIncidencia();
        when(handler.list()).thenReturn(List.of(incidencia));

        List<IncidenciaContrata> result = svc.list();

        assertThat(result).containsExactly(incidencia);
        verify(handler).list();
    }

    @Test
    void getDelegatesToHandler() {
        IncidenciaContrata incidencia = buildIncidencia();
        when(handler.get(incidencia.getId())).thenReturn(incidencia);

        IncidenciaContrata result = svc.get(incidencia.getId());

        assertThat(result).isEqualTo(incidencia);
        verify(handler).get(incidencia.getId());
    }

    @Test
    void createDelegatesToHandler() {
        IncidenciaContrata incidencia = buildIncidencia();
        when(handler.create(any())).thenReturn(incidencia);

        IncidenciaContrata result = svc.create(incidencia);

        assertThat(result).isEqualTo(incidencia);
        verify(handler).create(incidencia);
    }

    @Test
    void updateDelegatesToHandler() {
        IncidenciaContrata incidencia = buildIncidencia();
        when(handler.update(eq(incidencia.getId()), any())).thenReturn(incidencia);

        IncidenciaContrata result = svc.update(incidencia.getId(), incidencia);

        assertThat(result).isEqualTo(incidencia);
        verify(handler).update(incidencia.getId(), incidencia);
    }

    @Test
    void deleteDelegatesToHandler() {
        UUID id = UUID.randomUUID();

        svc.delete(id);

        verify(handler).delete(id);
    }
}
