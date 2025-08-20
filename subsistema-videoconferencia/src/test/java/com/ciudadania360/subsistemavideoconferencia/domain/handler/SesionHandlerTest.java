package com.ciudadania360.subsistemavideoconferencia.domain.handler;

import com.ciudadania360.subsistemavideoconferencia.domain.entity.Sesion;
import com.ciudadania360.subsistemavideoconferencia.domain.repository.SesionRepositorio;
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
class SesionHandlerTest {

    @Mock
    private SesionRepositorio repo;

    @InjectMocks
    private SesionHandler handler;

    @Test
    void listReturnsAllSesiones() {
        Sesion e = Sesion.builder()
                .id(UUID.randomUUID())
                .codigoAcceso("ABC123")
                .estado("Programada")
                .build();

        when(repo.findAll()).thenReturn(List.of(e));

        List<Sesion> result = handler.list();

        assertThat(result).containsExactly(e);
        verify(repo).findAll();
    }

    @Test
    void getReturnsSesionById() {
        UUID id = UUID.randomUUID();
        Sesion e = Sesion.builder()
                .id(id)
                .codigoAcceso("XYZ789")
                .estado("En Curso")
                .build();

        when(repo.findById(id)).thenReturn(Optional.of(e));

        Sesion result = handler.get(id);

        assertThat(result).isEqualTo(e);
        verify(repo).findById(id);
    }

    @Test
    void createSavesSesion() {
        Sesion e = Sesion.builder()
                .id(UUID.randomUUID())
                .codigoAcceso("NEW456")
                .estado("Finalizada")
                .build();

        when(repo.save(any())).thenReturn(e);

        Sesion result = handler.create(e);

        assertThat(result).isEqualTo(e);
        verify(repo).save(e);
    }

    @Test
    void updateSavesExistingSesion() {
        UUID id = UUID.randomUUID();
        Sesion e = Sesion.builder()
                .id(id)
                .codigoAcceso("UPD123")
                .estado("Cancelada")
                .build();

        when(repo.findById(id)).thenReturn(Optional.of(e));
        when(repo.save(any())).thenReturn(e);

        Sesion result = handler.update(id, e);

        assertThat(result).isEqualTo(e);
        verify(repo).save(e);
    }

    @Test
    void deleteRemovesSesionById() {
        UUID id = UUID.randomUUID();

        handler.delete(id);

        verify(repo).deleteById(id);
    }
}
