package com.ciudadania360.subsistemaciudadano.domain.handler;

import com.ciudadania360.subsistemaciudadano.domain.entity.Ciudadano;
import com.ciudadania360.subsistemaciudadano.domain.entity.Ubicacion;
import com.ciudadania360.subsistemaciudadano.domain.repository.CiudadanoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CiudadanoHandlerTest {

    @Mock
    private CiudadanoRepository repo;

    @InjectMocks
    private CiudadanoHandler handler;

    private Ciudadano buildCiudadano() {
        Ubicacion ubicacion = Ubicacion.builder()
                .id(UUID.randomUUID())
                .direccion("Calle Falsa 123")
                .municipio("Valencia")
                .provincia("Valencia")
                .cp("46000")
                .lat(39.4699)
                .lon(-0.3763)
                .precision(5)
                .fuente("Ciudadano")
                .metadata("{\"nota\":\"dato validado\"}")
                .version(1L)
                .build();

        return Ciudadano.builder()
                .id(UUID.randomUUID())
                .nifNie("12345678A")
                .nombre("Juan")
                .apellidos("Pérez Gómez")
                .email("juan@example.com")
                .telefono("600123456")
                .canalPreferido("Email")
                .direccionPostal("Calle Falsa 123")
                .ubicacion(ubicacion)
                .consentimientoLOPD(true)
                .estado("Activo")
                .externalId("EXT123")
                .metadata("{\"extra\":\"valor\"}")
                .solicitudes(new ArrayList<>())
                .direcciones(new ArrayList<>())
                .consentimientos(new ArrayList<>())
                .interacciones(new ArrayList<>())
                .version(1L)
                .build();
    }

    @Test
    void listReturnsAllCiudadanos() {
        Ciudadano e = buildCiudadano();
        when(repo.findAll()).thenReturn(List.of(e));

        List<Ciudadano> result = handler.list();

        assertThat(result).containsExactly(e);
        assertThat(result.get(0).getUbicacion().getMunicipio()).isEqualTo("Valencia");
        verify(repo).findAll();
    }

    @Test
    void getReturnsCiudadanoById() {
        Ciudadano e = buildCiudadano();
        when(repo.findById(e.getId())).thenReturn(Optional.of(e));

        Ciudadano result = handler.get(e.getId());

        assertThat(result).isEqualTo(e);
        assertThat(result.getUbicacion().getCp()).isEqualTo("46000");
        verify(repo).findById(e.getId());
    }

    @Test
    void createSavesCiudadano() {
        Ciudadano e = buildCiudadano();
        when(repo.save(any())).thenReturn(e);

        Ciudadano result = handler.create(e);

        assertThat(result).isEqualTo(e);
        assertThat(result.getUbicacion().getFuente()).isEqualTo("Ciudadano");
        verify(repo).save(e);
    }

    @Test
    void updateSavesExistingCiudadano() {
        Ciudadano e = buildCiudadano();
        when(repo.findById(e.getId())).thenReturn(Optional.of(e));
        when(repo.save(any())).thenReturn(e);

        Ciudadano result = handler.update(e.getId(), e);

        assertThat(result).isEqualTo(e);
        assertThat(result.getUbicacion().getLat()).isEqualTo(39.4699);
        verify(repo).save(e);
    }

    @Test
    void deleteRemovesCiudadanoById() {
        UUID id = UUID.randomUUID();

        // Stub: el repositorio dice que el ID existe
        when(repo.existsById(id)).thenReturn(true);
        doNothing().when(repo).deleteById(id);

        handler.delete(id);

        verify(repo).existsById(id);
        verify(repo).deleteById(id);
    }
}
