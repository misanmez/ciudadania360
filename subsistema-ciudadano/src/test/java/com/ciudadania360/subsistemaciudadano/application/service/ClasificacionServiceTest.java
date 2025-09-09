package com.ciudadania360.subsistemaciudadano.application.service;

import com.ciudadania360.shared.application.dto.clasificacion.ClasificacionRequest;
import com.ciudadania360.shared.application.dto.clasificacion.ClasificacionResponse;
import com.ciudadania360.subsistemaciudadano.application.mapper.ClasificacionMapper;
import com.ciudadania360.subsistemaciudadano.application.validator.ClasificacionValidator;
import com.ciudadania360.subsistemaciudadano.domain.entity.Clasificacion;
import com.ciudadania360.subsistemaciudadano.domain.handler.ClasificacionHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClasificacionServiceTest {

    @Mock
    private ClasificacionHandler handler;

    @Mock
    private ClasificacionMapper mapper;

    @Mock
    private ClasificacionValidator validator;

    @InjectMocks
    private ClasificacionService svc;

    private Clasificacion buildClasificacion() {
        return Clasificacion.builder()
                .id(UUID.randomUUID())
                .codigo("COD123")
                .nombre("Test")
                .descripcion("Descripción")
                .tipo("Solicitud")
                .padre(null)
                .hijos(new ArrayList<>())
                .version(1L)
                .build();
    }

    private ClasificacionRequest toRequest(Clasificacion e) {
        return ClasificacionRequest.builder()
                .codigo(e.getCodigo())
                .nombre(e.getNombre())
                .descripcion(e.getDescripcion())
                .tipo(e.getTipo())
                .padreId(e.getPadre() != null ? e.getPadre().getId() : null)
                .build();
    }

    private ClasificacionResponse toResponse(Clasificacion e) {
        return ClasificacionResponse.builder()
                .id(e.getId())
                .codigo(e.getCodigo())
                .nombre(e.getNombre())
                .descripcion(e.getDescripcion())
                .tipo(e.getTipo())
                .padreId(e.getPadre() != null ? e.getPadre().getId() : null)
                .hijos(new ArrayList<>()) // reflejamos hijos vacíos
                .version(e.getVersion())
                .build();
    }

    @Test
    void listDelegatesToHandler() {
        Clasificacion e = buildClasificacion();
        when(handler.list()).thenReturn(List.of(e));
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        List<ClasificacionResponse> result = svc.list();

        assertThat(result).containsExactly(toResponse(e));
        verify(handler).list();
        verify(mapper).toResponse(e);
    }

    @Test
    void getDelegatesToHandler() {
        Clasificacion e = buildClasificacion();
        when(handler.get(e.getId())).thenReturn(e);
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        ClasificacionResponse result = svc.get(e.getId());

        assertThat(result).isEqualTo(toResponse(e));
        verify(handler).get(e.getId());
        verify(mapper).toResponse(e);
    }

    @Test
    void createDelegatesToHandler() {
        Clasificacion e = buildClasificacion();
        ClasificacionRequest request = toRequest(e);
        ClasificacionResponse expectedResponse = toResponse(e);

        when(mapper.toEntity(request)).thenReturn(e);
        when(handler.create(e)).thenReturn(e);
        when(mapper.toResponse(e)).thenReturn(expectedResponse);

        ClasificacionResponse result = svc.create(request);

        assertThat(result).isEqualTo(expectedResponse);
        verify(validator).validateCreate(request);
        verify(mapper).toEntity(request);
        verify(handler).create(e);
        verify(mapper).toResponse(e);
    }

    @Test
    void updateDelegatesToHandler() {
        Clasificacion e = buildClasificacion();
        ClasificacionRequest request = new ClasificacionRequest("COD123", "Updated", "Desc", "Solicitud", null);

        when(handler.get(e.getId())).thenReturn(e);
        doNothing().when(mapper).updateEntity(same(e), eq(request));

        Clasificacion updated = Clasificacion.builder()
                .id(e.getId())
                .codigo(e.getCodigo())
                .nombre("Updated")
                .descripcion("Desc")
                .tipo("Solicitud")
                .padre(null)
                .hijos(new ArrayList<>())
                .version(1L)
                .build();

        when(handler.update(eq(e.getId()), same(e))).thenReturn(updated);
        when(mapper.toResponse(updated)).thenReturn(toResponse(updated));

        ClasificacionResponse result = svc.update(e.getId(), request);

        assertThat(result.getNombre()).isEqualTo("Updated");
        assertThat(result.getDescripcion()).isEqualTo("Desc");

        verify(validator).validateUpdate(e.getId(), request);
        verify(handler).get(e.getId());
        verify(mapper).updateEntity(same(e), eq(request));
        verify(handler).update(eq(e.getId()), same(e));
        verify(mapper).toResponse(updated);
    }

    @Test
    void deleteDelegatesToHandler() {
        UUID id = UUID.randomUUID();

        svc.delete(id);

        verify(handler).delete(id);
        verifyNoInteractions(mapper);
        verifyNoInteractions(validator);
    }
}
