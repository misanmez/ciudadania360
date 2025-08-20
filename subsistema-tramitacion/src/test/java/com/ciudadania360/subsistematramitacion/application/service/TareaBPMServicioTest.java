package com.ciudadania360.subsistematramitacion.application.service;

import com.ciudadania360.subsistematramitacion.domain.entity.TareaBPM;
import com.ciudadania360.subsistematramitacion.domain.handler.TareaBPMHandler;
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
class TareaBPMServicioTest {

    @Mock
    private TareaBPMHandler handler;

    @InjectMocks
    private TareaBPMServicio svc;

    private TareaBPM buildTareaBPM() {
        return TareaBPM.builder()
                .id(UUID.randomUUID())
                .procesoBpmId(UUID.randomUUID())
                .engineTaskId("task-123")
                .nombre("Tarea de prueba")
                .estado("EN_PROGRESO")
                .assignee("usuario1")
                .candidateGroup("grupo1")
                .dueDate(Instant.now().plusSeconds(3600))
                .priority(5)
                .variables("{\"var1\": \"value1\"}")
                .created(Instant.now())
                .completed(null)
                .build();
    }

    @Test
    void listDelegatesToHandler() {
        TareaBPM tarea = buildTareaBPM();
        when(handler.list()).thenReturn(List.of(tarea));

        List<TareaBPM> result = svc.list();

        assertThat(result).containsExactly(tarea);
        verify(handler).list();
    }

    @Test
    void getDelegatesToHandler() {
        TareaBPM tarea = buildTareaBPM();
        when(handler.get(tarea.getId())).thenReturn(tarea);

        TareaBPM result = svc.get(tarea.getId());

        assertThat(result).isEqualTo(tarea);
        verify(handler).get(tarea.getId());
    }

    @Test
    void createDelegatesToHandler() {
        TareaBPM tarea = buildTareaBPM();
        when(handler.create(any())).thenReturn(tarea);

        TareaBPM result = svc.create(tarea);

        assertThat(result).isEqualTo(tarea);
        verify(handler).create(tarea);
    }

    @Test
    void updateDelegatesToHandler() {
        TareaBPM tarea = buildTareaBPM();
        when(handler.update(eq(tarea.getId()), any())).thenReturn(tarea);

        TareaBPM result = svc.update(tarea.getId(), tarea);

        assertThat(result).isEqualTo(tarea);
        verify(handler).update(tarea.getId(), tarea);
    }

    @Test
    void deleteDelegatesToHandler() {
        UUID id = UUID.randomUUID();

        svc.delete(id);

        verify(handler).delete(id);
    }
}
