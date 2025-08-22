package com.ciudadania360.subsistemainformacion.application.service;

import com.ciudadania360.subsistemainformacion.application.dto.recomendacion.RecomendacionRequest;
import com.ciudadania360.subsistemainformacion.application.dto.recomendacion.RecomendacionResponse;
import com.ciudadania360.subsistemainformacion.application.mapper.RecomendacionMapper;
import com.ciudadania360.subsistemainformacion.domain.entity.Recomendacion;
import com.ciudadania360.subsistemainformacion.domain.handler.RecomendacionHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class RecomendacionServiceTest {

    @Mock
    private RecomendacionHandler handler;

    @Mock
    private RecomendacionMapper mapper;

    @InjectMocks
    private RecomendacionService svc;

    private Recomendacion buildEntity() {
        return Recomendacion.builder()
                .id(UUID.randomUUID())
                .titulo("Título de prueba")
                .detalle("Detalle de prueba")
                .build();
    }

    private RecomendacionRequest toRequest(Recomendacion e) {
        return new RecomendacionRequest(e.getTitulo(), e.getDetalle());
    }

    private RecomendacionResponse toResponse(Recomendacion e) {
        return new RecomendacionResponse(e.getId(), e.getTitulo(), e.getDetalle());
    }

    @Test
    void listDelegatesToHandler() {
        Recomendacion e = buildEntity();
        when(handler.list()).thenReturn(List.of(e));
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        List<RecomendacionResponse> result = svc.list();

        assertThat(result).containsExactly(toResponse(e));
        verify(handler).list();
        verify(mapper).toResponse(e);
    }

    @Test
    void getDelegatesToHandler() {
        Recomendacion e = buildEntity();
        when(handler.get(e.getId())).thenReturn(e);
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        RecomendacionResponse result = svc.get(e.getId());

        assertThat(result).isEqualTo(toResponse(e));
        verify(handler).get(e.getId());
        verify(mapper).toResponse(e);
    }

    @Test
    void createDelegatesToHandler() {
        Recomendacion e = Recomendacion.builder()
                .id(UUID.randomUUID())
                .titulo("Título recomendación")
                .detalle("Detalle de la recomendación")
                .build();

        RecomendacionRequest request = new RecomendacionRequest(e.getTitulo(), e.getDetalle());

        when(mapper.toEntity(request)).thenReturn(e);
        when(handler.create(any(Recomendacion.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(mapper.toResponse(any(Recomendacion.class))).thenAnswer(invocation -> toResponse(invocation.getArgument(0)));

        RecomendacionResponse result = svc.create(request);

        assertThat(result.getTitulo()).isEqualTo(e.getTitulo());
        assertThat(result.getDetalle()).isEqualTo(e.getDetalle());
        assertThat(result.getId()).isNotNull();

        verify(mapper).toEntity(request);
        verify(handler).create(any(Recomendacion.class));
        verify(mapper).toResponse(any(Recomendacion.class));
    }


    @Test
    void updateDelegatesToHandler() {
        Recomendacion e = buildEntity();
        RecomendacionRequest request = toRequest(e);

        when(handler.get(e.getId())).thenReturn(e);
        doNothing().when(mapper).updateEntity(e, request);
        when(handler.update(eq(e.getId()), same(e))).thenReturn(e);
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        RecomendacionResponse result = svc.update(e.getId(), request);

        assertThat(result).isEqualTo(toResponse(e));
        verify(handler).get(e.getId());
        verify(mapper).updateEntity(e, request);
        verify(handler).update(e.getId(), e);
        verify(mapper).toResponse(e);
    }

    @Test
    void deleteDelegatesToHandler() {
        UUID id = UUID.randomUUID();

        svc.delete(id);

        verify(handler).delete(id);
        verifyNoInteractions(mapper);
    }
}
