package com.ciudadania360.subsistematramitacion.application.service;

import com.ciudadania360.subsistematramitacion.domain.entity.Integracion;
import com.ciudadania360.subsistematramitacion.domain.handler.IntegracionHandler;
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
class IntegracionServicioTest {

    @Mock
    private IntegracionHandler handler;

    @InjectMocks
    private IntegracionServicio svc;

    private Integracion buildIntegracion() {
        return Integracion.builder()
                .id(UUID.randomUUID())
                .sistema("Sistema de prueba")
                .tipo("Tipo de prueba")
                .endpoint("http://endpoint.de.prueba")
                .build();
    }

    @Test
    void listDelegatesToHandler() {
        Integracion integracion = buildIntegracion();
        when(handler.list()).thenReturn(List.of(integracion));

        List<Integracion> result = svc.list();

        assertThat(result).containsExactly(integracion);
        verify(handler).list();
    }

    @Test
    void getDelegatesToHandler() {
        Integracion integracion = buildIntegracion();
        when(handler.get(integracion.getId())).thenReturn(integracion);

        Integracion result = svc.get(integracion.getId());

        assertThat(result).isEqualTo(integracion);
        verify(handler).get(integracion.getId());
    }

    @Test
    void createDelegatesToHandler() {
        Integracion integracion = buildIntegracion();
        when(handler.create(any())).thenReturn(integracion);

        Integracion result = svc.create(integracion);

        assertThat(result).isEqualTo(integracion);
        verify(handler).create(integracion);
    }

    @Test
    void updateDelegatesToHandler() {
        Integracion integracion = buildIntegracion();
        when(handler.update(eq(integracion.getId()), any())).thenReturn(integracion);

        Integracion result = svc.update(integracion.getId(), integracion);

        assertThat(result).isEqualTo(integracion);
        verify(handler).update(integracion.getId(), integracion);
    }

    @Test
    void deleteDelegatesToHandler() {
        UUID id = UUID.randomUUID();

        svc.delete(id);

        verify(handler).delete(id);
    }
}
