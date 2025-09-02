package com.ciudadania360.subsistematramitacion.domain.handler;

import com.ciudadania360.subsistematramitacion.domain.entity.ProcesoBPM;
import com.ciudadania360.subsistematramitacion.domain.repository.ProcesoBPMRepository;
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
class ProcesoBPMHandlerTest {

    @Mock
    private ProcesoBPMRepository repo;

    @InjectMocks
    private ProcesoBPMHandler handler;

    @Test
    void listReturnsAllProcesos() {
        ProcesoBPM e = ProcesoBPM.builder()
                .id(UUID.randomUUID())
                .engineInstanceId("engine-1")
                .definitionKey("def-1")
                .businessKey(UUID.randomUUID())
                .estado("EN_PROGRESO")
                .inicio(Instant.now())
                .fin(null)
                .variables("{}")
                .iniciador("usuario1")
                .build();

        when(repo.findAll()).thenReturn(List.of(e));

        List<ProcesoBPM> result = handler.list();

        assertThat(result).containsExactly(e);
        verify(repo).findAll();
    }

    @Test
    void getReturnsProcesoById() {
        UUID id = UUID.randomUUID();
        ProcesoBPM e = ProcesoBPM.builder()
                .id(id)
                .engineInstanceId("engine-2")
                .definitionKey("def-2")
                .businessKey(UUID.randomUUID())
                .estado("COMPLETADO")
                .inicio(Instant.now())
                .fin(Instant.now())
                .variables("{}")
                .iniciador("usuario2")
                .build();

        when(repo.findById(id)).thenReturn(Optional.of(e));

        ProcesoBPM result = handler.get(id);

        assertThat(result).isEqualTo(e);
        verify(repo).findById(id);
    }

    @Test
    void createSavesProceso() {
        ProcesoBPM e = ProcesoBPM.builder()
                .id(UUID.randomUUID())
                .engineInstanceId("engine-3")
                .definitionKey("def-3")
                .businessKey(UUID.randomUUID())
                .estado("EN_ESPERA")
                .inicio(Instant.now())
                .fin(null)
                .variables("{}")
                .iniciador("usuario3")
                .build();

        when(repo.save(any())).thenReturn(e);

        ProcesoBPM result = handler.create(e);

        assertThat(result).isEqualTo(e);
        verify(repo).save(e);
    }

    @Test
    void updateSavesExistingProceso() {
        UUID id = UUID.randomUUID();
        ProcesoBPM e = ProcesoBPM.builder()
                .id(id)
                .engineInstanceId("engine-4")
                .definitionKey("def-4")
                .businessKey(UUID.randomUUID())
                .estado("EN_PROGRESO")
                .inicio(Instant.now())
                .fin(null)
                .variables("{}")
                .iniciador("usuario4")
                .build();

        when(repo.findById(id)).thenReturn(Optional.of(e));
        when(repo.save(any())).thenReturn(e);

        ProcesoBPM result = handler.update(id, e);

        assertThat(result).isEqualTo(e);
        verify(repo).save(e);
    }

    @Test
    void deleteRemovesProcesoById() {
        UUID id = UUID.randomUUID();

        handler.delete(id);

        verify(repo).deleteById(id);
    }
}
