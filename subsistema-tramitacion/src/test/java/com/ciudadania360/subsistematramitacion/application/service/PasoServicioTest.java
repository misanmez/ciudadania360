package com.ciudadania360.subsistematramitacion.application.service;

import com.ciudadania360.subsistematramitacion.domain.entity.Paso;
import com.ciudadania360.subsistematramitacion.domain.handler.PasoHandler;
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
class PasoServicioTest {

    @Mock
    private PasoHandler handler;

    @InjectMocks
    private PasoServicio svc;

    private Paso buildPaso() {
        return Paso.builder()
                .id(UUID.randomUUID())
                .flujoId(UUID.randomUUID())
                .nombre("Paso de Prueba")
                .orden(1)
                .tipo("Tipo de prueba")
                .responsableRole("Role de prueba")
                .slaHoras(24)
                .build();
    }

    @Test
    void listDelegatesToHandler() {
        Paso paso = buildPaso();
        when(handler.list()).thenReturn(List.of(paso));

        List<Paso> result = svc.list();

        assertThat(result).containsExactly(paso);
        verify(handler).list();
    }

    @Test
    void getDelegatesToHandler() {
        Paso paso = buildPaso();
        when(handler.get(paso.getId())).thenReturn(paso);

        Paso result = svc.get(paso.getId());

        assertThat(result).isEqualTo(paso);
        verify(handler).get(paso.getId());
    }

    @Test
    void createDelegatesToHandler() {
        Paso paso = buildPaso();
        when(handler.create(any())).thenReturn(paso);

        Paso result = svc.create(paso);

        assertThat(result).isEqualTo(paso);
        verify(handler).create(paso);
    }

    @Test
    void updateDelegatesToHandler() {
        Paso paso = buildPaso();
        when(handler.update(eq(paso.getId()), any())).thenReturn(paso);

        Paso result = svc.update(paso.getId(), paso);

        assertThat(result).isEqualTo(paso);
        verify(handler).update(paso.getId(), paso);
    }

    @Test
    void deleteDelegatesToHandler() {
        UUID id = UUID.randomUUID();

        svc.delete(id);

        verify(handler).delete(id);
    }
}
