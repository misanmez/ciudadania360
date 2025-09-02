package com.ciudadania360.subsistemainformacion.domain.handler;

import com.ciudadania360.subsistemainformacion.domain.entity.Indicador;
import com.ciudadania360.subsistemainformacion.domain.repository.IndicadorRepository;
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
class IndicadorHandlerTest {

    @Mock
    private IndicadorRepository repo;

    @InjectMocks
    private IndicadorHandler handler;

    @Test
    void listReturnsAllIndicadores() {
        Indicador e = Indicador.builder()
                .id(UUID.randomUUID())
                .nombre("Indicador de Ejemplo")
                .descripcion("Descripción del indicador")
                .build();

        when(repo.findAll()).thenReturn(List.of(e));

        List<Indicador> result = handler.list();

        assertThat(result).containsExactly(e);
        verify(repo).findAll();
    }

    @Test
    void getReturnsIndicadorById() {
        UUID id = UUID.randomUUID();
        Indicador e = Indicador.builder()
                .id(id)
                .nombre("Nombre ejemplo")
                .descripcion("Descripción ejemplo")
                .build();
        when(repo.findById(id)).thenReturn(Optional.of(e));

        Indicador result = handler.get(id);

        assertThat(result).isEqualTo(e);
        verify(repo).findById(id);
    }

    @Test
    void createSavesIndicador() {
        Indicador e = Indicador.builder()
                .id(UUID.randomUUID())
                .nombre("Nuevo indicador")
                .descripcion("Nueva descripción")
                .build();
        when(repo.save(any())).thenReturn(e);

        Indicador result = handler.create(e);

        assertThat(result).isEqualTo(e);
        verify(repo).save(e);
    }

    @Test
    void updateSavesExistingIndicador() {
        UUID id = UUID.randomUUID();
        Indicador e = Indicador.builder()
                .id(id)
                .nombre("Indicador original")
                .descripcion("Descripción original")
                .build();
        when(repo.findById(id)).thenReturn(Optional.of(e));
        when(repo.save(any())).thenReturn(e);

        Indicador result = handler.update(id, e);

        assertThat(result).isEqualTo(e);
        verify(repo).save(e);
    }

    @Test
    void deleteRemovesIndicadorById() {
        UUID id = UUID.randomUUID();

        handler.delete(id);

        verify(repo).deleteById(id);
    }
}
