package com.ciudadania360.subsistemaciudadano.domain.handler;

import com.ciudadania360.subsistemaciudadano.domain.entity.Ubicacion;
import com.ciudadania360.subsistemaciudadano.domain.repository.UbicacionRepositorio;
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
class UbicacionHandlerTest {

    @Mock
    private UbicacionRepositorio repo;

    @InjectMocks
    private UbicacionHandler handler;

    private Ubicacion createUbicacion(UUID id) {
        return Ubicacion.builder()
                .id(id)
                .direccion("Calle Falsa 123")
                .municipio("Madrid")
                .provincia("Madrid")
                .cp("28001")
                .lat(40.4168)
                .lon(-3.7038)
                .precision(5)
                .fuente("Sistema Test")
                .build();
    }

    @Test
    void listReturnsAllUbicaciones() {
        Ubicacion e = createUbicacion(UUID.randomUUID());

        when(repo.findAll()).thenReturn(List.of(e));

        List<Ubicacion> result = handler.obtenerTodos();

        assertThat(result).containsExactly(e);
        verify(repo).findAll();
    }

    @Test
    void getReturnsUbicacionById() {
        UUID id = UUID.randomUUID();
        Ubicacion e = createUbicacion(id);

        when(repo.findById(id)).thenReturn(Optional.of(e));

        Ubicacion result = handler.obtenerPorId(id);

        assertThat(result).isEqualTo(e);
        verify(repo).findById(id);
    }

    @Test
    void createSavesUbicacion() {
        Ubicacion e = createUbicacion(UUID.randomUUID());

        when(repo.save(any())).thenReturn(e);

        Ubicacion result = handler.crear(e);

        assertThat(result).isEqualTo(e);
        verify(repo).save(e);
    }

    @Test
    void updateSavesExistingUbicacion() {
        UUID id = UUID.randomUUID();

        // Ubicacion existente en la BD
        Ubicacion existente = createUbicacion(id);

        // Ubicacion con cambios (mismo id)
        Ubicacion cambios = createUbicacion(id);
        cambios.setDireccion("Avenida Siempre Viva 742");
        cambios.setMunicipio("Barcelona");
        cambios.setCp("08001");

        when(repo.findById(id)).thenReturn(Optional.of(existente));
        when(repo.save(any())).thenReturn(existente);

        Ubicacion result = handler.actualizar(id, cambios);

        // Verificar que los cambios fueron aplicados
        assertThat(result.getDireccion()).isEqualTo("Avenida Siempre Viva 742");
        assertThat(result.getMunicipio()).isEqualTo("Barcelona");
        assertThat(result.getCp()).isEqualTo("08001");

        // Asegurar que el objeto guardado es el existente, modificado
        verify(repo).save(existente);
    }


    @Test
    void deleteRemovesUbicacionById() {
        UUID id = UUID.randomUUID();

        handler.eliminar(id);

        verify(repo).deleteById(id);
    }
}