package com.ciudadania360.subsistematramitacion.domain.handler;

import com.ciudadania360.subsistematramitacion.domain.entity.IncidenciaContrata;
import com.ciudadania360.subsistematramitacion.domain.repository.IncidenciaContrataRepositorio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IncidenciaContrataHandlerTest {

    @Mock
    private IncidenciaContrataRepositorio repo;

    @InjectMocks
    private IncidenciaContrataHandler handler;

    @Test
    void listReturnsAllIncidencias() {
        IncidenciaContrata e = IncidenciaContrata.builder()
                .id(UUID.randomUUID())
                .contrataId(UUID.randomUUID())
                .descripcion("Descripción de la incidencia")
                .estado("Pendiente")
                .build();

        when(repo.findAll()).thenReturn(List.of(e));

        List<IncidenciaContrata> result = handler.list();

        assertThat(result).containsExactly(e);
        verify(repo).findAll();
    }

    @Test
    void getReturnsIncidenciaById() {
        UUID id = UUID.randomUUID();
        IncidenciaContrata e = IncidenciaContrata.builder()
                .id(id)
                .contrataId(UUID.randomUUID())
                .descripcion("Descripción de la incidencia")
                .estado("Pendiente")
                .build();

        when(repo.findById(id)).thenReturn(Optional.of(e));

        IncidenciaContrata result = handler.get(id);

        assertThat(result).isEqualTo(e);
        verify(repo).findById(id);
    }

    @Test
    void createSavesIncidencia() {
        IncidenciaContrata e = IncidenciaContrata.builder()
                .id(UUID.randomUUID())
                .contrataId(UUID.randomUUID())
                .descripcion("Nueva incidencia")
                .estado("Pendiente")
                .build();

        when(repo.save(any())).thenReturn(e);

        IncidenciaContrata result = handler.create(e);

        assertThat(result).isEqualTo(e);
        verify(repo).save(e);
    }

    @Test
    void updateSavesExistingIncidencia() {
        UUID id = UUID.randomUUID();
        IncidenciaContrata e = IncidenciaContrata.builder()
                .id(id)
                .contrataId(UUID.randomUUID())
                .descripcion("Incidencia original")
                .estado("Pendiente")
                .build();

        when(repo.findById(id)).thenReturn(Optional.of(e));
        when(repo.save(any())).thenReturn(e);

        IncidenciaContrata result = handler.update(id, e);

        assertThat(result).isEqualTo(e);
        verify(repo).save(e);
    }

    @Test
    void deleteRemovesIncidenciaById() {
        UUID id = UUID.randomUUID();

        handler.delete(id);

        verify(repo).deleteById(id);
    }
}
