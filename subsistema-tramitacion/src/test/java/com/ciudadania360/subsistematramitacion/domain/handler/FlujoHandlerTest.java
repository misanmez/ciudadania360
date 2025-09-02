package com.ciudadania360.subsistematramitacion.domain.handler;

import com.ciudadania360.subsistematramitacion.domain.entity.Flujo;
import com.ciudadania360.subsistematramitacion.domain.repository.FlujoRepository;
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
class FlujoHandlerTest {

    @Mock
    private FlujoRepository repo;

    @InjectMocks
    private FlujoHandler handler;

    @Test
    void listReturnsAllFlujos() {
        Flujo e = Flujo.builder()
                .id(UUID.randomUUID())
                .nombre("Flujo de Prueba")
                .descripcion("Descripción del flujo de prueba")
                .activo(true)
                .tipo("Normal")
                .slaHoras(24)
                .pasosDefinition("{\"paso1\":\"inicio\"}")
                .build();

        when(repo.findAll()).thenReturn(List.of(e));

        List<Flujo> result = handler.list();

        assertThat(result).containsExactly(e);
        verify(repo).findAll();
    }

    @Test
    void getReturnsFlujoById() {
        UUID id = UUID.randomUUID();
        Flujo e = Flujo.builder()
                .id(id)
                .nombre("Flujo Ejemplo")
                .descripcion("Flujo de ejemplo")
                .activo(true)
                .build();

        when(repo.findById(id)).thenReturn(Optional.of(e));

        Flujo result = handler.get(id);

        assertThat(result).isEqualTo(e);
        verify(repo).findById(id);
    }

    @Test
    void createSavesFlujo() {
        Flujo e = Flujo.builder()
                .id(UUID.randomUUID())
                .nombre("Nuevo Flujo")
                .descripcion("Descripción del nuevo flujo")
                .activo(true)
                .build();

        when(repo.save(any())).thenReturn(e);

        Flujo result = handler.create(e);

        assertThat(result).isEqualTo(e);
        verify(repo).save(e);
    }

    @Test
    void updateSavesExistingFlujo() {
        UUID id = UUID.randomUUID();
        Flujo e = Flujo.builder()
                .id(id)
                .nombre("Flujo Original")
                .descripcion("Descripción original")
                .activo(true)
                .build();

        when(repo.findById(id)).thenReturn(Optional.of(e));
        when(repo.save(any())).thenReturn(e);

        Flujo result = handler.update(id, e);

        assertThat(result).isEqualTo(e);
        verify(repo).save(e);
    }

    @Test
    void deleteRemovesFlujoById() {
        UUID id = UUID.randomUUID();

        handler.delete(id);

        verify(repo).deleteById(id);
    }
}
