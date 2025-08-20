package com.ciudadania360.subsistemainformacion.domain.handler;

import com.ciudadania360.subsistemainformacion.domain.entity.Sugerencia;
import com.ciudadania360.subsistemainformacion.domain.repository.SugerenciaRepositorio;
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
class SugerenciaHandlerTest {

    @Mock
    private SugerenciaRepositorio repo;

    @InjectMocks
    private SugerenciaHandler handler;

    @Test
    void listReturnsAllSugerencias() {
        Sugerencia e = Sugerencia.builder()
                .id(UUID.randomUUID())
                .titulo("Ejemplo de Sugerencia")
                .ciudadanoId(UUID.randomUUID())
                .descripcion("Descripción ejemplo")
                .build();

        when(repo.findAll()).thenReturn(List.of(e));

        List<Sugerencia> result = handler.list();

        assertThat(result).containsExactly(e);
        verify(repo).findAll();
    }

    @Test
    void getReturnsSugerenciaById() {
        UUID id = UUID.randomUUID();
        Sugerencia e = Sugerencia.builder()
                .id(id)
                .titulo("Ejemplo")
                .ciudadanoId(UUID.randomUUID())
                .descripcion("Detalle ejemplo")
                .build();
        when(repo.findById(id)).thenReturn(Optional.of(e));

        Sugerencia result = handler.get(id);

        assertThat(result).isEqualTo(e);
        verify(repo).findById(id);
    }

    @Test
    void createSavesSugerencia() {
        Sugerencia e = Sugerencia.builder()
                .id(UUID.randomUUID())
                .titulo("Nueva sugerencia")
                .ciudadanoId(UUID.randomUUID())
                .descripcion("Detalle nuevo")
                .build();
        when(repo.save(any())).thenReturn(e);

        Sugerencia result = handler.create(e);

        assertThat(result).isEqualTo(e);
        verify(repo).save(e);
    }

    @Test
    void updateSavesExistingSugerencia() {
        UUID id = UUID.randomUUID();
        Sugerencia e = Sugerencia.builder()
                .id(id)
                .titulo("Original")
                .ciudadanoId(UUID.randomUUID())
                .descripcion("Detalle original")
                .build();
        when(repo.findById(id)).thenReturn(Optional.of(e));
        when(repo.save(any())).thenReturn(e);

        Sugerencia result = handler.update(id, e);

        assertThat(result).isEqualTo(e);
        verify(repo).save(e);
    }

    @Test
    void deleteRemovesSugerenciaById() {
        UUID id = UUID.randomUUID();

        handler.delete(id);

        verify(repo).deleteById(id);
    }
}
