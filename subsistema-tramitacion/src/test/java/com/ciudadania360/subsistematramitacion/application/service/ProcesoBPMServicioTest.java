package com.ciudadania360.subsistematramitacion.application.service;

import com.ciudadania360.subsistematramitacion.domain.entity.ProcesoBPM;
import com.ciudadania360.subsistematramitacion.domain.handler.ProcesoBPMHandler;
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
class ProcesoBPMServicioTest {

    @Mock
    private ProcesoBPMHandler handler;

    @InjectMocks
    private ProcesoBPMServicio svc;

    private ProcesoBPM buildProcesoBPM() {
        return ProcesoBPM.builder()
                .id(UUID.randomUUID())
                .engineInstanceId("engine-123")
                .definitionKey("proc-def-01")
                .businessKey(UUID.randomUUID())
                .estado("EN_PROGRESO")
                .inicio(Instant.now())
                .fin(null)
                .variables("{\"var1\": \"value1\"}")
                .iniciador("usuario")
                .build();
    }

    @Test
    void listDelegatesToHandler() {
        ProcesoBPM proceso = buildProcesoBPM();
        when(handler.list()).thenReturn(List.of(proceso));

        List<ProcesoBPM> result = svc.list();

        assertThat(result).containsExactly(proceso);
        verify(handler).list();
    }

    @Test
    void getDelegatesToHandler() {
        ProcesoBPM proceso = buildProcesoBPM();
        when(handler.get(proceso.getId())).thenReturn(proceso);

        ProcesoBPM result = svc.get(proceso.getId());

        assertThat(result).isEqualTo(proceso);
        verify(handler).get(proceso.getId());
    }

    @Test
    void createDelegatesToHandler() {
        ProcesoBPM proceso = buildProcesoBPM();
        when(handler.create(any())).thenReturn(proceso);

        ProcesoBPM result = svc.create(proceso);

        assertThat(result).isEqualTo(proceso);
        verify(handler).create(proceso);
    }

    @Test
    void updateDelegatesToHandler() {
        ProcesoBPM proceso = buildProcesoBPM();
        when(handler.update(eq(proceso.getId()), any())).thenReturn(proceso);

        ProcesoBPM result = svc.update(proceso.getId(), proceso);

        assertThat(result).isEqualTo(proceso);
        verify(handler).update(proceso.getId(), proceso);
    }

    @Test
    void deleteDelegatesToHandler() {
        UUID id = UUID.randomUUID();

        svc.delete(id);

        verify(handler).delete(id);
    }
}
