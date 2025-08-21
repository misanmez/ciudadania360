package com.ciudadania360.subsistemaciudadano.application.service;

import com.ciudadania360.subsistemaciudadano.application.dto.ubicacion.UbicacionRequest;
import com.ciudadania360.subsistemaciudadano.application.dto.ubicacion.UbicacionResponse;
import com.ciudadania360.subsistemaciudadano.application.mapper.UbicacionMapper;
import com.ciudadania360.subsistemaciudadano.domain.entity.Ubicacion;
import com.ciudadania360.subsistemaciudadano.domain.handler.UbicacionHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UbicacionServiceTest {

    @Mock
    private UbicacionHandler handler;

    @Mock
    private UbicacionMapper mapper;

    @InjectMocks
    private UbicacionService svc;

    private Ubicacion buildUbicacion() {
        UUID id = UUID.fromString("00000000-0000-0000-0000-000000000001");
        return Ubicacion.builder()
                .id(id)
                .direccion("Calle Falsa 123")
                .municipio("Madrid")
                .provincia("Madrid")
                .cp("28080")
                .lat(40.4168)
                .lon(-3.7038)
                .precision(1)
                .fuente("Manual")
                .version(1L)
                .build();
    }

    private UbicacionRequest toRequest(Ubicacion u) {
        return new UbicacionRequest(
                u.getDireccion(),
                u.getMunicipio(),
                u.getProvincia(),
                u.getCp(),
                u.getLat(),
                u.getLon(),
                u.getPrecision(),
                u.getFuente()
        );
    }

    private UbicacionResponse toResponse(Ubicacion u) {
        return new UbicacionResponse(
                u.getId(),
                u.getDireccion(),
                u.getMunicipio(),
                u.getProvincia(),
                u.getCp(),
                u.getLat(),
                u.getLon(),
                u.getPrecision(),
                u.getFuente()
        );
    }

    @Test
    void listDelegatesToHandler() {
        Ubicacion u = buildUbicacion();
        UbicacionResponse expectedResponse = toResponse(u);

        when(handler.list()).thenReturn(List.of(u));
        when(mapper.toResponse(u)).thenReturn(expectedResponse);

        List<UbicacionResponse> result = svc.list();

        assertThat(result).containsExactly(expectedResponse);
        verify(handler).list();
        verify(mapper).toResponse(u);
    }

    @Test
    void getDelegatesToHandler() {
        Ubicacion u = buildUbicacion();
        UbicacionResponse expectedResponse = toResponse(u);

        when(handler.get(u.getId())).thenReturn(u);
        when(mapper.toResponse(u)).thenReturn(expectedResponse);

        UbicacionResponse result = svc.get(u.getId());

        assertThat(result).isEqualTo(expectedResponse);
        verify(handler).get(u.getId());
        verify(mapper).toResponse(u);
    }

    @Test
    void createDelegatesToHandler() {
        Ubicacion u = buildUbicacion();
        UbicacionRequest request = toRequest(u);
        UbicacionResponse expectedResponse = toResponse(u);

        when(mapper.toEntity(request)).thenReturn(u);
        when(handler.create(u)).thenReturn(u);
        when(mapper.toResponse(u)).thenReturn(expectedResponse);

        UbicacionResponse result = svc.create(request);

        assertThat(result).isEqualTo(expectedResponse);
        verify(mapper).toEntity(request);
        verify(handler).create(u);
        verify(mapper).toResponse(u);
    }

    @Test
    void updateDelegatesToHandler() {
        Ubicacion u = buildUbicacion();
        UbicacionRequest request = toRequest(u);
        UbicacionResponse expectedResponse = toResponse(u);

        when(handler.get(u.getId())).thenReturn(u);
        doNothing().when(mapper).updateEntity(u, request);
        when(handler.update(u.getId(), u)).thenReturn(u);
        when(mapper.toResponse(u)).thenReturn(expectedResponse);

        UbicacionResponse result = svc.update(u.getId(), request);

        assertThat(result).isEqualTo(expectedResponse);
        verify(handler).get(u.getId());
        verify(mapper).updateEntity(u, request);
        verify(handler).update(u.getId(), u);
        verify(mapper).toResponse(u);
    }

    @Test
    void deleteDelegatesToHandler() {
        UUID id = UUID.fromString("00000000-0000-0000-0000-000000000002");

        svc.delete(id);

        verify(handler).delete(id);
        verifyNoInteractions(mapper);
    }
}
