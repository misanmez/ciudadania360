package com.ciudadania360.subsistematramitacion.domain.handler;

import com.ciudadania360.subsistematramitacion.domain.entity.Carpeta;
import com.ciudadania360.subsistematramitacion.domain.repository.CarpetaRepositorio;
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
class CarpetaHandlerTest {

    @Mock
    private CarpetaRepositorio repo;

    @InjectMocks
    private CarpetaHandler handler;

    @Test
    void listReturnsAllCarpetas() {
        Carpeta e = Carpeta.builder()
                .id(UUID.randomUUID())
                .nombre("Carpeta de Prueba")
                .descripcion("Descripción de la carpeta de prueba")
                .build();

        when(repo.findAll()).thenReturn(List.of(e));

        List<Carpeta> result = handler.list();

        assertThat(result).containsExactly(e);
        verify(repo).findAll();
    }

    @Test
    void getReturnsCarpetaById() {
        UUID id = UUID.randomUUID();
        Carpeta e = Carpeta.builder().id(id).nombre("Carpeta de Prueba").build();
        when(repo.findById(id)).thenReturn(Optional.of(e));

        Carpeta result = handler.get(id);

        assertThat(result).isEqualTo(e);
        verify(repo).findById(id);
    }

    @Test
    void createSavesCarpeta() {
        Carpeta e = Carpeta.builder()
                .id(UUID.randomUUID())
                .nombre("Nueva Carpeta")
                .descripcion("Descripción nueva")
                .build();
        when(repo.save(any())).thenReturn(e);

        Carpeta result = handler.create(e);

        assertThat(result).isEqualTo(e);
        verify(repo).save(e);
    }

    @Test
    void updateSavesExistingCarpeta() {
        UUID id = UUID.randomUUID();
        Carpeta e = Carpeta.builder().id(id).nombre("Carpeta Original").build();
        when(repo.findById(id)).thenReturn(Optional.of(e));
        when(repo.save(any())).thenReturn(e);

        Carpeta result = handler.update(id, e);

        assertThat(result).isEqualTo(e);
        verify(repo).save(e);
    }

    @Test
    void deleteRemovesCarpetaById() {
        UUID id = UUID.randomUUID();

        handler.delete(id);

        verify(repo).deleteById(id);
    }
}
