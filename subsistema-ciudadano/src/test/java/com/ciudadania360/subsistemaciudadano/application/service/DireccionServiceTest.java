package com.ciudadania360.subsistemaciudadano.application.service;

import com.ciudadania360.subsistemaciudadano.application.dto.direccion.DireccionRequest;
import com.ciudadania360.subsistemaciudadano.application.dto.direccion.DireccionResponse;
import com.ciudadania360.subsistemaciudadano.application.mapper.DireccionMapper;
import com.ciudadania360.subsistemaciudadano.application.validator.DireccionValidator;
import com.ciudadania360.subsistemaciudadano.domain.entity.Ciudadano;
import com.ciudadania360.subsistemaciudadano.domain.entity.Direccion;
import com.ciudadania360.subsistemaciudadano.domain.handler.DireccionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class DireccionServiceTest {

    @Mock
    private DireccionHandler handler;

    @Mock
    private DireccionMapper mapper;

    @Mock
    private DireccionValidator validator;

    @InjectMocks
    private DireccionService service;

    @BeforeEach
    void initMocks() {
        MockitoAnnotations.openMocks(this);
    }

    private Direccion buildEntity() {
        Ciudadano ciudadano = Ciudadano.builder()
                .id(UUID.fromString("00000000-0000-0000-0000-000000000001"))
                .build();

        return Direccion.builder()
                .id(UUID.randomUUID())
                .ciudadano(ciudadano)
                .via("Calle Mayor")
                .numero("12")
                .cp("46000")
                .municipio("Valencia")
                .provincia("Valencia")
                .lat(39.47)
                .lon(-0.38)
                .principal(true)
                .version(1L)
                .build();
    }

    private DireccionRequest buildRequest(UUID ciudadanoId) {
        return DireccionRequest.builder()
                .ciudadanoId(ciudadanoId)
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

    private DireccionResponse toResponse(Direccion entity) {
        return DireccionResponse.builder()
                .id(entity.getId())
                .ciudadanoId(entity.getCiudadano().getId())
                .via(entity.getVia())
                .numero(entity.getNumero())
                .cp(entity.getCp())
                .municipio(entity.getMunicipio())
                .provincia(entity.getProvincia())
                .lat(entity.getLat())
                .lon(entity.getLon())
                .principal(entity.getPrincipal())
                .version(entity.getVersion())
                .build();
    }

    @Test
    void listDelegatesToHandler() {
        Direccion entity = buildEntity();
        when(handler.list()).thenReturn(List.of(entity));
        when(mapper.toResponse(entity)).thenReturn(toResponse(entity));

        List<DireccionResponse> result = service.list();

        assertThat(result).containsExactly(toResponse(entity));
        verify(handler).list();
        verify(mapper).toResponse(entity);
    }

    @Test
    void getDelegatesToHandler() {
        Direccion entity = buildEntity();
        when(handler.get(entity.getId())).thenReturn(entity);
        when(mapper.toResponse(entity)).thenReturn(toResponse(entity));

        DireccionResponse result = service.get(entity.getId());

        assertThat(result).isEqualTo(toResponse(entity));
        verify(handler).get(entity.getId());
        verify(mapper).toResponse(entity);
    }

    @Test
    void createDelegatesToHandler() {
        Direccion entity = buildEntity();
        DireccionRequest request = buildRequest(entity.getCiudadano().getId());
        DireccionResponse expectedResponse = toResponse(entity);

        when(mapper.toEntity(request)).thenReturn(entity);
        when(handler.create(entity)).thenReturn(entity);
        when(mapper.toResponse(entity)).thenReturn(expectedResponse);

        DireccionResponse result = service.create(request);

        assertThat(result).isEqualTo(expectedResponse);
        verify(validator).validate(request, true);
        verify(mapper).toEntity(request);
        verify(handler).create(entity);
        verify(mapper).toResponse(entity);
    }

    @Test
    void updateDelegatesToHandler() {
        Direccion entity = buildEntity();
        DireccionRequest request = buildRequest(entity.getCiudadano().getId());

        doNothing().when(mapper).updateEntity(entity, request);
        when(handler.get(entity.getId())).thenReturn(entity);
        when(handler.update(entity.getId(), entity)).thenReturn(entity);
        when(mapper.toResponse(entity)).thenReturn(toResponse(entity));

        DireccionResponse result = service.update(entity.getId(), request);

        assertThat(result).isEqualTo(toResponse(entity));
        verify(validator).validate(request, false);
        verify(handler).get(entity.getId());
        verify(mapper).updateEntity(entity, request);
        verify(handler).update(entity.getId(), entity);
        verify(mapper).toResponse(entity);
    }

    @Test
    void deleteDelegatesToHandler() {
        UUID id = UUID.randomUUID();
        Direccion existing = buildEntity();

        when(handler.get(id)).thenReturn(existing);

        service.delete(id);

        verify(handler).get(id);
        verify(validator).validateForDelete(existing);
        verify(handler).delete(id);
        verifyNoMoreInteractions(mapper); // el mapper sigue sin interacciones
    }
}
