package com.ciudadania360.subsistematramitacion.domain.handler;

import com.ciudadania360.subsistematramitacion.domain.entity.Paso;
import com.ciudadania360.subsistematramitacion.domain.repository.PasoRepository;
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
class PasoHandlerTest {

    @Mock
    private PasoRepository repo;

    @InjectMocks
    private PasoHandler handler;

    @Test
    void listReturnsAllPasos() {
        Paso e = Paso.builder()
                .id(UUID.randomUUID())
                .flujoId(UUID.randomUUID())
                .nombre("Paso de Prueba")
                .orden(1)
                .tipo("Tipo A")
                .responsableRole("Role A")
                .slaHoras(24)
                .build();

        when(repo.findAll()).thenReturn(List.of(e));

        List<Paso> result = handler.list();

        assertThat(result).containsExactly(e);
        verify(repo).findAll();
    }

    @Test
    void getReturnsPasoById() {
        UUID id = UUID.randomUUID();
        Paso e = Paso.builder()
                .id(id)
                .flujoId(UUID.randomUUID())
                .nombre("Paso de Prueba")
                .orden(1)
                .tipo("Tipo A")
                .responsableRole("Role A")
                .slaHoras(24)
                .build();

        when(repo.findById(id)).thenReturn(Optional.of(e));

        Paso result = handler.get(id);

        assertThat(result).isEqualTo(e);
        verify(repo).findById(id);
    }

    @Test
    void createSavesPaso() {
        Paso e = Paso.builder()
                .id(UUID.randomUUID())
                .flujoId(UUID.randomUUID())
                .nombre("Nuevo Paso")
                .orden(2)
                .tipo("Tipo B")
                .responsableRole("Role B")
                .slaHoras(12)
                .build();

        when(repo.save(any())).thenReturn(e);

        Paso result = handler.create(e);

        assertThat(result).isEqualTo(e);
        verify(repo).save(e);
    }

    @Test
    void updateSavesExistingPaso() {
        UUID id = UUID.randomUUID();
        Paso e = Paso.builder()
                .id(id)
                .flujoId(UUID.randomUUID())
                .nombre("Paso Actualizado")
                .orden(1)
                .tipo("Tipo C")
                .responsableRole("Role C")
                .slaHoras(8)
                .build();

        when(repo.findById(id)).thenReturn(Optional.of(e));
        when(repo.save(any())).thenReturn(e);

        Paso result = handler.update(id, e);

        assertThat(result).isEqualTo(e);
        verify(repo).save(e);
    }

    @Test
    void deleteRemovesPasoById() {
        UUID id = UUID.randomUUID();

        handler.delete(id);

        verify(repo).deleteById(id);
    }
}
