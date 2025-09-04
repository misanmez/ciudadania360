package com.ciudadania360.subsistemaciudadano.application.service;

import com.ciudadania360.subsistemaciudadano.application.dto.ubicacion.UbicacionRequest;
import com.ciudadania360.subsistemaciudadano.application.dto.ubicacion.UbicacionResponse;
import com.ciudadania360.subsistemaciudadano.application.mapper.UbicacionMapper;
import com.ciudadania360.subsistemaciudadano.application.validator.UbicacionValidator;
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

    @Mock private UbicacionHandler handler;
    @Mock private UbicacionMapper mapper;
    @Mock private UbicacionValidator validator;
    @InjectMocks private UbicacionService svc;

    private Ubicacion buildUbicacion() {
        return Ubicacion.builder()
                .id(UUID.fromString("00000000-0000-0000-0000-000000000001"))
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
                u.getFuente(),
                u.getVersion()
        );
    }

    @Test
    void listDelegatesToHandler() {
        Ubicacion u = buildUbicacion();
        UbicacionResponse r = toResponse(u);

        when(handler.list()).thenReturn(List.of(u));
        when(mapper.toResponse(u)).thenReturn(r);

        List<UbicacionResponse> result = svc.list();

        assertThat(result).containsExactly(r);
        verify(handler).list();
        verify(mapper).toResponse(u);
        verifyNoMoreInteractions(handler, mapper);
    }

    @Test
    void getDelegatesToHandler() {
        Ubicacion u = buildUbicacion();
        UbicacionResponse r = toResponse(u);

        when(handler.get(u.getId())).thenReturn(u);
        doNothing().when(validator).validateExistence(any(Ubicacion.class));
        when(mapper.toResponse(u)).thenReturn(r);

        UbicacionResponse result = svc.get(u.getId());

        assertThat(result).isEqualTo(r);
        verify(handler).get(u.getId());
        verify(validator).validateExistence(any(Ubicacion.class));
        verify(mapper).toResponse(u);
    }

    @Test
    void createDelegatesToHandlerAndValidator() {
        Ubicacion u = buildUbicacion();
        UbicacionRequest req = toRequest(u);
        UbicacionResponse r = toResponse(u);

        doNothing().when(validator).validateCreate(req);
        when(mapper.toEntity(req)).thenReturn(u);
        when(handler.create(u)).thenReturn(u);
        when(mapper.toResponse(u)).thenReturn(r);

        UbicacionResponse result = svc.create(req);

        assertThat(result).isEqualTo(r);
        verify(validator).validateCreate(req);
        verify(mapper).toEntity(req);
        verify(handler).create(u);
        verify(mapper).toResponse(u);
    }

    @Test
    void updateDelegatesToHandlerAndValidator() {
        Ubicacion u = buildUbicacion();
        UbicacionRequest req = toRequest(u);
        UbicacionResponse r = toResponse(u);

        when(handler.get(u.getId())).thenReturn(u);
        doNothing().when(validator).validateExistence(any(Ubicacion.class));
        doNothing().when(validator).validateUpdate(req);
        doNothing().when(mapper).updateEntity(u, req);
        when(handler.update(u.getId(), u)).thenReturn(u);
        when(mapper.toResponse(u)).thenReturn(r);

        UbicacionResponse result = svc.update(u.getId(), req);

        assertThat(result).isEqualTo(r);
        verify(handler).get(u.getId());
        verify(validator).validateExistence(any(Ubicacion.class));
        verify(validator).validateUpdate(req);
        verify(mapper).updateEntity(u, req);
        verify(handler).update(u.getId(), u);
        verify(mapper).toResponse(u);
    }

    @Test
    void deleteDelegatesToHandlerAndValidator() {
        Ubicacion u = buildUbicacion();

        when(handler.get(u.getId())).thenReturn(u);
        doNothing().when(validator).validateExistence(any(Ubicacion.class));
        doNothing().when(handler).delete(u.getId());

        svc.delete(u.getId());

        verify(handler).get(u.getId());
        verify(validator).validateExistence(any(Ubicacion.class));
        verify(handler).delete(u.getId());
        verifyNoInteractions(mapper);
    }
}
