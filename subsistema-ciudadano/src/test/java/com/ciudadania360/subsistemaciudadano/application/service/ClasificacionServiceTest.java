package com.ciudadania360.subsistemaciudadano.application.service;

import com.ciudadania360.subsistemaciudadano.application.dto.ClasificacionRequest;
import com.ciudadania360.subsistemaciudadano.application.dto.ClasificacionResponse;
import com.ciudadania360.subsistemaciudadano.application.mapper.ClasificacionMapper;
import com.ciudadania360.subsistemaciudadano.domain.entity.Clasificacion;
import com.ciudadania360.subsistemaciudadano.domain.handler.ClasificacionHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClasificacionServiceTest {

    @Mock
    private ClasificacionHandler handler;

    @Mock
    private ClasificacionMapper mapper; // 🔑 Mock del mapper

    @InjectMocks
    private ClasificacionService svc;

    private Clasificacion buildClasificacion() {
        return Clasificacion.builder()
                .id(UUID.randomUUID())
                .nombre("Test")
                .descripcion("Descripción")
                .version(1L)
                .build();
    }

    private ClasificacionRequest toRequest(Clasificacion e) {
        return new ClasificacionRequest(
                e.getNombre(),
                e.getDescripcion()
        );
    }

    private ClasificacionResponse toResponse(Clasificacion e) {
        return new ClasificacionResponse(
                e.getId(),
                e.getNombre(),
                e.getDescripcion(),
                e.getVersion()
        );
    }

    @Test
    void listDelegatesToHandler() {
        Clasificacion e = buildClasificacion();
        when(handler.list()).thenReturn(List.of(e));
        when(mapper.toResponse(e)).thenReturn(toResponse(e)); // 🔑 stub mapping

        List<ClasificacionResponse> result = svc.list();

        assertThat(result).containsExactly(toResponse(e));
        verify(handler).list();
        verify(mapper).toResponse(e);
    }

    @Test
    void getDelegatesToHandler() {
        Clasificacion e = buildClasificacion();
        when(handler.get(e.getId())).thenReturn(e);
        when(mapper.toResponse(e)).thenReturn(toResponse(e)); // 🔑 stub mapping

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
        verify(mapper).toEntity(request);
        verify(handler).create(e);
        verify(mapper).toResponse(e);
    }


    @Test
    void updateDelegatesToHandler() {
        Clasificacion e = buildClasificacion();
        ClasificacionRequest request = new ClasificacionRequest("Updated", "Desc");

        // 1) Recupera la existente
        when(handler.get(e.getId())).thenReturn(e);

        // 2) El mapper actualiza la entidad existente (es void; en un mock no hace nada por defecto)
        doNothing().when(mapper).updateEntity(same(e), eq(request));

        // 3) El handler persiste y devuelve la actualizada
        Clasificacion updated = Clasificacion.builder()
                .id(e.getId())
                .nombre("Updated")
                .descripcion("Desc")
                .version(1L)
                .build();

        when(handler.update(eq(e.getId()), same(e))).thenReturn(updated);

        // 4) Mapeo a response
        when(mapper.toResponse(updated)).thenReturn(toResponse(updated));

        ClasificacionResponse result = svc.update(e.getId(), request);

        assertThat(result.getNombre()).isEqualTo("Updated");
        assertThat(result.getDescripcion()).isEqualTo("Desc");

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
        verifyNoInteractions(mapper); // opcional
    }
}
