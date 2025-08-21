package com.ciudadania360.subsistemaciudadano.application.service;

import com.ciudadania360.subsistemaciudadano.application.dto.direccion.DireccionRequest;
import com.ciudadania360.subsistemaciudadano.application.dto.direccion.DireccionResponse;
import com.ciudadania360.subsistemaciudadano.application.mapper.DireccionMapper;
import com.ciudadania360.subsistemaciudadano.domain.entity.Direccion;
import com.ciudadania360.subsistemaciudadano.domain.handler.DireccionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class DireccionServiceTest {

    private DireccionHandler handler;
    private DireccionMapper mapper;
    private DireccionService service;

    @BeforeEach
    void setUp() {
        handler = mock(DireccionHandler.class);
        mapper = mock(DireccionMapper.class);
        service = new DireccionService(handler, mapper);
    }

    private Direccion buildEntity() {
        return Direccion.builder()
                .id(UUID.randomUUID())
                .ciudadanoId(UUID.randomUUID())
                .via("Calle Mayor")
                .numero("12")
                .cp("46000")
                .municipio("Valencia")
                .provincia("Valencia")
                .lat(39.47)
                .lon(-0.38)
                .principal(true)
                .build();
    }

    private DireccionRequest buildRequest() {
        return DireccionRequest.builder()
                .ciudadanoId(UUID.randomUUID())
                .via("Gran Via")
                .numero("45")
                .cp("28000")
                .municipio("Madrid")
                .provincia("Madrid")
                .lat(40.41)
                .lon(-3.70)
                .principal(false)
                .build();
    }

    @Test
    void createDelegatesToHandler() {
        DireccionRequest request = buildRequest();
        Direccion entity = buildEntity();
        DireccionResponse response = DireccionResponse.builder().via(request.getVia()).build();

        when(mapper.toEntity(request)).thenReturn(entity);
        when(handler.create(entity)).thenReturn(entity);
        when(mapper.toResponse(entity)).thenReturn(response);

        DireccionResponse result = service.create(request);

        assertThat(result).isNotNull();
        assertThat(result.getVia()).isEqualTo(request.getVia());
        verify(handler, times(1)).create(entity);
        verify(mapper, times(1)).toEntity(request);
        verify(mapper, times(1)).toResponse(entity);
    }

    @Test
    void updateDelegatesToHandler() {
        Direccion entity = buildEntity();
        DireccionRequest request = buildRequest();
        DireccionResponse response = DireccionResponse.builder().via(request.getVia()).municipio(request.getMunicipio()).build();

        when(handler.get(entity.getId())).thenReturn(entity);
        doNothing().when(mapper).updateEntity(entity, request);
        when(handler.update(entity.getId(), entity)).thenReturn(entity);
        when(mapper.toResponse(entity)).thenReturn(response);

        DireccionResponse result = service.update(entity.getId(), request);

        assertThat(result).isNotNull();
        assertThat(result.getVia()).isEqualTo(request.getVia());
        assertThat(result.getMunicipio()).isEqualTo(request.getMunicipio());
        verify(handler, times(1)).get(entity.getId());
        verify(mapper, times(1)).updateEntity(entity, request);
        verify(handler, times(1)).update(entity.getId(), entity);
        verify(mapper, times(1)).toResponse(entity);
    }

    @Test
    void getDelegatesToHandler() {
        Direccion entity = buildEntity();
        DireccionResponse response = DireccionResponse.builder().via(entity.getVia()).build();

        when(handler.get(entity.getId())).thenReturn(entity);
        when(mapper.toResponse(entity)).thenReturn(response);

        DireccionResponse result = service.get(entity.getId());

        assertThat(result).isNotNull();
        assertThat(result.getVia()).isEqualTo(entity.getVia());
        verify(handler, times(1)).get(entity.getId());
        verify(mapper, times(1)).toResponse(entity);
    }

    @Test
    void deleteDelegatesToHandler() {
        UUID id = UUID.randomUUID();

        service.delete(id);

        verify(handler, times(1)).delete(id);
    }
}
