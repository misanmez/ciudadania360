package com.ciudadania360.subsistematramitacion.application.service;

import com.ciudadania360.subsistematramitacion.domain.entity.Flujo;
import com.ciudadania360.subsistematramitacion.domain.handler.FlujoHandler;
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
class FlujoServicioTest {

    @Mock
    private FlujoHandler handler;

    @InjectMocks
    private FlujoServicio svc;

    private Flujo buildFlujo() {
        return Flujo.builder()
                .id(UUID.randomUUID())
                .nombre("Flujo de Prueba")
                .descripcion("Descripción del flujo de prueba")
                .activo(true)
                .tipo("Tipo A")
                .slaHoras(48)
                .pasosDefinition("{\"pasos\":[]}")
                .build();
    }

    @Test
    void listDelegatesToHandler() {
        Flujo flujo = buildFlujo();
        when(handler.list()).thenReturn(List.of(flujo));

        List<Flujo> result = svc.list();

        assertThat(result).containsExactly(flujo);
        verify(handler).list();
    }

    @Test
    void getDelegatesToHandler() {
        Flujo flujo = buildFlujo();
        when(handler.get(flujo.getId())).thenReturn(flujo);

        Flujo result = svc.get(flujo.getId());

        assertThat(result).isEqualTo(flujo);
        verify(handler).get(flujo.getId());
    }

    @Test
    void createDelegatesToHandler() {
        Flujo flujo = buildFlujo();
        when(handler.create(any())).thenReturn(flujo);

        Flujo result = svc.create(flujo);

        assertThat(result).isEqualTo(flujo);
        verify(handler).create(flujo);
    }

    @Test
    void updateDelegatesToHandler() {
        Flujo flujo = buildFlujo();
        when(handler.update(eq(flujo.getId()), any())).thenReturn(flujo);

        Flujo result = svc.update(flujo.getId(), flujo);

        assertThat(result).isEqualTo(flujo);
        verify(handler).update(flujo.getId(), flujo);
    }

    @Test
    void deleteDelegatesToHandler() {
        UUID id = UUID.randomUUID();

        svc.delete(id);

        verify(handler).delete(id);
    }
}
