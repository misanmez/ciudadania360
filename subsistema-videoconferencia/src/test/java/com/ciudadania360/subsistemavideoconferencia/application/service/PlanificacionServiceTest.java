package com.ciudadania360.subsistemavideoconferencia.application.service;

import com.ciudadania360.subsistemavideoconferencia.application.dto.planificacion.PlanificacionRequest;
import com.ciudadania360.subsistemavideoconferencia.application.dto.planificacion.PlanificacionResponse;
import com.ciudadania360.subsistemavideoconferencia.application.mapper.PlanificacionMapper;
import com.ciudadania360.subsistemavideoconferencia.domain.entity.Planificacion;
import com.ciudadania360.subsistemavideoconferencia.domain.handler.PlanificacionHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class PlanificacionServiceTest {

    @Mock private PlanificacionHandler handler;
    @Mock private PlanificacionMapper mapper;
    @InjectMocks private PlanificacionService svc;

    private static final UUID FIXED_ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private Planificacion buildEntity() {
        return Planificacion.builder()
                .id(FIXED_ID)
                .nombre("Planificación 1")
                .descripcion("Descripción de prueba")
                .build();
    }

    private PlanificacionRequest toRequest(Planificacion e) {
        return new PlanificacionRequest(e.getNombre(), e.getDescripcion());
    }

    private PlanificacionResponse toResponse(Planificacion e) {
        return new PlanificacionResponse(e.getId(), e.getNombre(), e.getDescripcion());
    }

    @Test void listDelegatesToHandler() {
        Planificacion e = buildEntity();
        when(handler.list()).thenReturn(List.of(e));
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        List<PlanificacionResponse> result = svc.list();

        assertThat(result).containsExactly(toResponse(e));
        verify(handler).list();
        verify(mapper).toResponse(e);
    }

    @Test void getDelegatesToHandler() {
        Planificacion e = buildEntity();
        when(handler.get(e.getId())).thenReturn(e);
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        PlanificacionResponse result = svc.get(e.getId());

        assertThat(result).isEqualTo(toResponse(e));
        verify(handler).get(e.getId());
        verify(mapper).toResponse(e);
    }

    @Test void createDelegatesToHandler() {
        Planificacion e = buildEntity();
        PlanificacionRequest request = toRequest(e);

        when(mapper.toEntity(request)).thenReturn(e);
        when(handler.create(any(Planificacion.class))).thenReturn(e);
        when(mapper.toResponse(any(Planificacion.class))).thenReturn(toResponse(e));

        PlanificacionResponse result = svc.create(request);

        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(toResponse(e));

        verify(mapper).toEntity(request);
        verify(handler).create(any(Planificacion.class));
        verify(mapper).toResponse(any(Planificacion.class));
    }

    @Test void updateDelegatesToHandler() {
        Planificacion e = buildEntity();
        PlanificacionRequest request = toRequest(e);

        when(handler.get(e.getId())).thenReturn(e);
        doNothing().when(mapper).updateEntity(e, request);
        when(handler.update(eq(e.getId()), same(e))).thenReturn(e);
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        PlanificacionResponse result = svc.update(e.getId(), request);

        assertThat(result).isEqualTo(toResponse(e));
        verify(handler).get(e.getId());
        verify(mapper).updateEntity(e, request);
        verify(handler).update(e.getId(), e);
        verify(mapper).toResponse(e);
    }

    @Test void deleteDelegatesToHandler() {
        UUID id = UUID.randomUUID();
        svc.delete(id);
        verify(handler).delete(id);
        verifyNoInteractions(mapper);
    }
}
