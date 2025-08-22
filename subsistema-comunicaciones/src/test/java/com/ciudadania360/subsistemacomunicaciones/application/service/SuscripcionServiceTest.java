package com.ciudadania360.subsistemacomunicaciones.application.service;

import com.ciudadania360.subsistemacomunicaciones.application.dto.suscripcion.SuscripcionRequest;
import com.ciudadania360.subsistemacomunicaciones.application.dto.suscripcion.SuscripcionResponse;
import com.ciudadania360.subsistemacomunicaciones.application.mapper.SuscripcionMapper;
import com.ciudadania360.subsistemacomunicaciones.domain.entity.Suscripcion;
import com.ciudadania360.subsistemacomunicaciones.domain.handler.SuscripcionHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class SuscripcionServiceTest {

    @Mock
    private SuscripcionHandler handler;

    @Mock
    private SuscripcionMapper mapper;

    @InjectMocks
    private SuscripcionService svc;

    private Suscripcion buildSuscripcion() {
        return Suscripcion.builder()
                .id(UUID.randomUUID())
                .ciudadanoId(UUID.randomUUID())
                .tema("Noticias")
                .activo(true)
                .version(0L)
                .build();
    }

    private SuscripcionRequest toRequest(Suscripcion e) {
        return new SuscripcionRequest(e.getCiudadanoId(), e.getTema(), e.getActivo());
    }

    private SuscripcionResponse toResponse(Suscripcion e) {
        return new SuscripcionResponse(e.getId(), e.getCiudadanoId(), e.getTema(), e.getActivo());
    }

    @Test
    void listDelegatesToHandler() {
        Suscripcion e = buildSuscripcion();
        when(handler.list()).thenReturn(List.of(e));
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        List<SuscripcionResponse> result = svc.list();

        assertThat(result).containsExactly(toResponse(e));
        verify(handler).list();
        verify(mapper).toResponse(e);
    }

    @Test
    void getDelegatesToHandler() {
        Suscripcion e = buildSuscripcion();
        when(handler.get(e.getId())).thenReturn(e);
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        SuscripcionResponse result = svc.get(e.getId());

        assertThat(result).isEqualTo(toResponse(e));
        verify(handler).get(e.getId());
        verify(mapper).toResponse(e);
    }

    @Test
    void createDelegatesToHandler() {
        Suscripcion e = buildSuscripcion();
        SuscripcionRequest request = toRequest(e);
        SuscripcionResponse expected = toResponse(e);

        when(mapper.toEntity(request)).thenReturn(e);
        when(handler.create(e)).thenReturn(e);
        when(mapper.toResponse(e)).thenReturn(expected);

        SuscripcionResponse result = svc.create(request);

        assertThat(result).isEqualTo(expected);
        verify(mapper).toEntity(request);
        verify(handler).create(e);
        verify(mapper).toResponse(e);
    }

    @Test
    void updateDelegatesToHandler() {
        Suscripcion e = buildSuscripcion();
        SuscripcionRequest request = toRequest(e);

        when(handler.get(e.getId())).thenReturn(e);
        doNothing().when(mapper).updateEntity(e, request);
        when(handler.update(e.getId(), e)).thenReturn(e);
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        SuscripcionResponse result = svc.update(e.getId(), request);

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
