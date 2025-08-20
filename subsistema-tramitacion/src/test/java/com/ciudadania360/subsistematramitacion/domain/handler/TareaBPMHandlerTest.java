package com.ciudadania360.subsistematramitacion.domain.handler;

import com.ciudadania360.subsistematramitacion.domain.entity.TareaBPM;
import com.ciudadania360.subsistematramitacion.domain.repository.TareaBPMRepositorio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TareaBPMHandlerTest {

    @Mock
    private TareaBPMRepositorio repo;

    @InjectMocks
    private TareaBPMHandler handler;

    @Test
    void listReturnsAllTareas() {
        TareaBPM e = TareaBPM.builder()
                .id(UUID.randomUUID())
                .procesoBpmId(UUID.randomUUID())
                .engineTaskId("task-1")
                .nombre("Tarea de prueba")
                .estado("EN_PROGRESO")
                .assignee("usuario1")
                .candidateGroup("grupo1")
                .dueDate(Instant.now().plusSeconds(3600))
                .priority(1)
                .variables("{}")
                .created(Instant.now())
                .completed(null)
                .build();

        when(repo.findAll()).thenReturn(List.of(e));

        List<TareaBPM> result = handler.list();

        assertThat(result).containsExactly(e);
        verify(repo).findAll();
    }

    @Test
    void getReturnsTareaById() {
        UUID id = UUID.randomUUID();
        TareaBPM e = TareaBPM.builder()
                .id(id)
                .procesoBpmId(UUID.randomUUID())
                .engineTaskId("task-2")
                .nombre("Tarea específica")
                .estado("COMPLETADA")
                .assignee("usuario2")
                .candidateGroup("grupo2")
                .dueDate(Instant.now().plusSeconds(7200))
                .priority(2)
                .variables("{}")
                .created(Instant.now())
                .completed(Instant.now())
                .build();

        when(repo.findById(id)).thenReturn(Optional.of(e));

        TareaBPM result = handler.get(id);

        assertThat(result).isEqualTo(e);
        verify(repo).findById(id);
    }

    @Test
    void createSavesTarea() {
        TareaBPM e = TareaBPM.builder()
                .id(UUID.randomUUID())
                .procesoBpmId(UUID.randomUUID())
                .engineTaskId("task-3")
                .nombre("Nueva Tarea")
                .estado("EN_ESPERA")
                .assignee("usuario3")
                .candidateGroup("grupo3")
                .dueDate(Instant.now().plusSeconds(3600))
                .priority(3)
                .variables("{}")
                .created(Instant.now())
                .completed(null)
                .build();

        when(repo.save(any())).thenReturn(e);

        TareaBPM result = handler.create(e);

        assertThat(result).isEqualTo(e);
        verify(repo).save(e);
    }

    @Test
    void updateSavesExistingTarea() {
        UUID id = UUID.randomUUID();
        TareaBPM e = TareaBPM.builder()
                .id(id)
                .procesoBpmId(UUID.randomUUID())
                .engineTaskId("task-4")
                .nombre("Tarea actualizada")
                .estado("EN_PROGRESO")
                .assignee("usuario4")
                .candidateGroup("grupo4")
                .dueDate(Instant.now().plusSeconds(3600))
                .priority(4)
                .variables("{}")
                .created(Instant.now())
                .completed(null)
                .build();

        when(repo.findById(id)).thenReturn(Optional.of(e));
        when(repo.save(any())).thenReturn(e);

        TareaBPM result = handler.update(id, e);

        assertThat(result).isEqualTo(e);
        verify(repo).save(e);
    }

    @Test
    void deleteRemovesTareaById() {
        UUID id = UUID.randomUUID();

        handler.delete(id);

        verify(repo).deleteById(id);
    }
}
