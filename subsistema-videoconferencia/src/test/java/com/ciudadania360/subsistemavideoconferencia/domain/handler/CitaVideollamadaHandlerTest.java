package com.ciudadania360.subsistemavideoconferencia.domain.handler;

import com.ciudadania360.subsistemavideoconferencia.domain.entity.CitaVideollamada;
import com.ciudadania360.subsistemavideoconferencia.domain.repository.CitaVideollamadaRepositorio;
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
class CitaVideollamadaHandlerTest {

    @Mock
    private CitaVideollamadaRepositorio repo;

    @InjectMocks
    private CitaVideollamadaHandler handler;

    @Test
    void listReturnsAllCitas() {
        CitaVideollamada e = CitaVideollamada.builder()
                .id(UUID.randomUUID())
                .sesionId(UUID.randomUUID())
                .ciudadanoId(UUID.randomUUID())
                .fechaProgramadaInicio(Instant.now())
                .fechaProgramadaFin(Instant.now().plusSeconds(3600))
                .estado("Programada")
                .empleadoId("empleado1")
                .canalNotificacion("Email")
                .notas("Notas de prueba")
                .build();

        when(repo.findAll()).thenReturn(List.of(e));

        List<CitaVideollamada> result = handler.list();

        assertThat(result).containsExactly(e);
        verify(repo).findAll();
    }

    @Test
    void getReturnsCitaById() {
        UUID id = UUID.randomUUID();
        CitaVideollamada e = CitaVideollamada.builder().id(id).build();
        when(repo.findById(id)).thenReturn(Optional.of(e));

        CitaVideollamada result = handler.get(id);

        assertThat(result).isEqualTo(e);
        verify(repo).findById(id);
    }

    @Test
    void createSavesCita() {
        CitaVideollamada e = CitaVideollamada.builder()
                .id(UUID.randomUUID())
                .build();
        when(repo.save(any())).thenReturn(e);

        CitaVideollamada result = handler.create(e);

        assertThat(result).isEqualTo(e);
        verify(repo).save(e);
    }

    @Test
    void updateSavesExistingCita() {
        UUID id = UUID.randomUUID();
        CitaVideollamada e = CitaVideollamada.builder().id(id).build();
        when(repo.findById(id)).thenReturn(Optional.of(e));
        when(repo.save(any())).thenReturn(e);

        CitaVideollamada result = handler.update(id, e);

        assertThat(result).isEqualTo(e);
        verify(repo).save(e);
    }

    @Test
    void deleteRemovesCitaById() {
        UUID id = UUID.randomUUID();

        handler.delete(id);

        verify(repo).deleteById(id);
    }
}
