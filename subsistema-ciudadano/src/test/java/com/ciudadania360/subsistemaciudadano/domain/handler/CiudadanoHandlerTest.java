package com.ciudadania360.subsistemaciudadano.domain.handler;

import com.ciudadania360.subsistemaciudadano.domain.entity.Ciudadano;
import com.ciudadania360.subsistemaciudadano.domain.repository.CiudadanoRepository;
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
class CiudadanoHandlerTest {

    @Mock
    private CiudadanoRepository repo;

    @InjectMocks
    private CiudadanoHandler handler;

    @Test
    void listReturnsAllCiudadanos() {
        Ciudadano e = Ciudadano.builder()
                .id(UUID.randomUUID())
                .nifNie("12345678A")
                .nombre("Juan")
                .apellidos("Pérez Gómez")
                .email("juan@example.com")
                .telefono("600123456")
                .canalPreferido("Email")
                .direccionPostal("Calle Falsa 123")
                .ubicacionId(UUID.randomUUID())
                .consentimientoLOPD(true)
                .estado("Activo")
                .externalId("EXT123")
                .metadata("{}")
                .build();
        when(repo.findAll()).thenReturn(List.of(e));

        List<Ciudadano> result = handler.list();

        assertThat(result).containsExactly(e);
        verify(repo).findAll();
    }

    @Test
    void getReturnsCiudadanoById() {
        UUID id = UUID.randomUUID();
        Ciudadano e = Ciudadano.builder().id(id).nombre("Juan").build();
        when(repo.findById(id)).thenReturn(Optional.of(e));

        Ciudadano result = handler.get(id);

        assertThat(result).isEqualTo(e);
        verify(repo).findById(id);
    }

    @Test
    void createSavesCiudadano() {
        Ciudadano e = Ciudadano.builder().id(UUID.randomUUID()).nombre("Juan").build();
        when(repo.save(any())).thenReturn(e);

        Ciudadano result = handler.create(e);

        assertThat(result).isEqualTo(e);
        verify(repo).save(e);
    }

    @Test
    void updateSavesExistingCiudadano() {
        UUID id = UUID.randomUUID();
        Ciudadano e = Ciudadano.builder().id(id).nombre("Juan").build();

        when(repo.findById(id)).thenReturn(Optional.of(e));
        when(repo.save(any())).thenReturn(e);

        Ciudadano result = handler.update(id, e);

        assertThat(result).isEqualTo(e);
        verify(repo).save(e);
    }

    @Test
    void deleteRemovesCiudadanoById() {
        UUID id = UUID.randomUUID();

        handler.delete(id);

        verify(repo).deleteById(id);
    }
}