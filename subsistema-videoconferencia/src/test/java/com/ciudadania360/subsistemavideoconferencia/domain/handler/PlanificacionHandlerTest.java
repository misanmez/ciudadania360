package com.ciudadania360.subsistemavideoconferencia.domain.handler;

import com.ciudadania360.subsistemavideoconferencia.domain.entity.Planificacion;
import com.ciudadania360.subsistemavideoconferencia.domain.repository.PlanificacionRepository;
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
class PlanificacionHandlerTest {

    @Mock
    private PlanificacionRepository repo;

    @InjectMocks
    private PlanificacionHandler handler;

    @Test
    void listReturnsAllPlanificaciones() {
        Planificacion e = Planificacion.builder()
                .id(UUID.randomUUID())
                .nombre("Planificación de Prueba")
                .descripcion("Descripción de la planificación de prueba")
                .build();

        when(repo.findAll()).thenReturn(List.of(e));

        List<Planificacion> result = handler.list();

        assertThat(result).containsExactly(e);
        verify(repo).findAll();
    }

    @Test
    void getReturnsPlanificacionById() {
        UUID id = UUID.randomUUID();
        Planificacion e = Planificacion.builder().id(id).nombre("Planificación de Prueba").build();
        when(repo.findById(id)).thenReturn(Optional.of(e));

        Planificacion result = handler.get(id);

        assertThat(result).isEqualTo(e);
        verify(repo).findById(id);
    }

    @Test
    void createSavesPlanificacion() {
        Planificacion e = Planificacion.builder()
                .id(UUID.randomUUID())
                .nombre("Nueva Planificación")
                .descripcion("Descripción nueva")
                .build();
        when(repo.save(any())).thenReturn(e);

        Planificacion result = handler.create(e);

        assertThat(result).isEqualTo(e);
        verify(repo).save(e);
    }

    @Test
    void updateSavesExistingPlanificacion() {
        UUID id = UUID.randomUUID();
        Planificacion e = Planificacion.builder().id(id).nombre("Planificación Original").build();
        when(repo.findById(id)).thenReturn(Optional.of(e));
        when(repo.save(any())).thenReturn(e);

        Planificacion result = handler.update(id, e);

        assertThat(result).isEqualTo(e);
        verify(repo).save(e);
    }

    @Test
    void deleteRemovesPlanificacionById() {
        UUID id = UUID.randomUUID();

        handler.delete(id);

        verify(repo).deleteById(id);
    }
}
