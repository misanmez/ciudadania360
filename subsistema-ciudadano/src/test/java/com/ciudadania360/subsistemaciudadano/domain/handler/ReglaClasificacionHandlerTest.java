package com.ciudadania360.subsistemaciudadano.domain.handler;

import com.ciudadania360.subsistemaciudadano.domain.entity.ReglaClasificacion;
import com.ciudadania360.subsistemaciudadano.domain.repository.ReglaClasificacionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReglaClasificacionHandlerTest {

    @Mock
    private ReglaClasificacionRepository repo;

    @InjectMocks
    private ReglaClasificacionHandler handler;

    @Test
    void listReturnsAllReglas() {
        ReglaClasificacion e = ReglaClasificacion.builder()
                .id(UUID.randomUUID())
                .nombre("Regla X")
                .expresion("expresion_test")
                .prioridad(1)
                .activa(true)
                .clasificacionId(UUID.randomUUID())
                .condiciones("{\"condicion\":\"valor\"}")
                .fuente("Fuente A")
                .vigenciaDesde(Instant.now())
                .vigenciaHasta(Instant.now().plusSeconds(3600))
                .build();

        when(repo.findAll()).thenReturn(List.of(e));

        List<ReglaClasificacion> result = handler.list();

        assertThat(result).containsExactly(e);
        verify(repo).findAll();
    }

    @Test
    void getReturnsReglaById() {
        UUID id = UUID.randomUUID();
        ReglaClasificacion e = ReglaClasificacion.builder()
                .id(id)
                .nombre("Regla X")
                .expresion("expresion_test")
                .build();

        when(repo.findById(id)).thenReturn(Optional.of(e));

        ReglaClasificacion result = handler.get(id);

        assertThat(result).isEqualTo(e);
        verify(repo).findById(id);
    }

    @Test
    void createSavesRegla() {
        ReglaClasificacion e = ReglaClasificacion.builder()
                .id(UUID.randomUUID())
                .nombre("Regla X")
                .expresion("expresion_test")
                .build();

        when(repo.save(any())).thenReturn(e);

        ReglaClasificacion result = handler.create(e);

        assertThat(result).isEqualTo(e);
        verify(repo).save(e);
    }

    @Test
    void updateSavesExistingRegla() {
        UUID id = UUID.randomUUID();

        // Regla existente en la "BD"
        ReglaClasificacion existente = ReglaClasificacion.builder()
                .id(id)
                .nombre("Regla X")
                .expresion("expresion_test")
                .build();

        // Cambios que vienen en la request
        ReglaClasificacion cambios = ReglaClasificacion.builder()
                .id(id)
                .nombre("Regla Y")
                .expresion("nueva_expresion")
                .build();

        when(repo.findById(id)).thenReturn(Optional.of(existente));
        when(repo.save(any())).thenReturn(existente);

        ReglaClasificacion result = handler.update(id, cambios);

        // Verificar que los cambios se aplicaron en la entidad existente
        assertThat(result.getNombre()).isEqualTo("Regla Y");
        assertThat(result.getExpresion()).isEqualTo("nueva_expresion");

        // Comprobar que se guarda la entidad ya existente (modificada)
        verify(repo).save(existente);
    }


    @Test
    void deleteRemovesReglaById() {
        UUID id = UUID.randomUUID();

        handler.delete(id);

        verify(repo).deleteById(id);
    }
}