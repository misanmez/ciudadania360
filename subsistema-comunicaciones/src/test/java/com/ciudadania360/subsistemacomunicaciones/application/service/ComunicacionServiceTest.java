package com.ciudadania360.subsistemacomunicaciones.application.service;

import com.ciudadania360.subsistemacomunicaciones.application.dto.comunicacion.ComunicacionRequest;
import com.ciudadania360.subsistemacomunicaciones.application.dto.comunicacion.ComunicacionResponse;
import com.ciudadania360.subsistemacomunicaciones.application.mapper.ComunicacionMapper;
import com.ciudadania360.subsistemacomunicaciones.domain.entity.Comunicacion;
import com.ciudadania360.subsistemacomunicaciones.domain.handler.ComunicacionHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class ComunicacionServiceTest {

    @Mock
    private ComunicacionHandler handler;

    @Mock
    private ComunicacionMapper mapper;

    @InjectMocks
    private ComunicacionService svc;

    private Comunicacion buildComunicacion() {
        return Comunicacion.builder()
                .id(UUID.randomUUID())
                .asunto("Asunto prueba")
                .cuerpo("Cuerpo del mensaje")
                .canal("Email")
                .estado("PENDIENTE")
                .destinatario("juan@example.com")
                .ciudadanoId(UUID.randomUUID())
                .programadaPara(Instant.now())
                .version(0L)
                .build();
    }

    private ComunicacionRequest toRequest(Comunicacion e) {
        return new ComunicacionRequest(
                e.getAsunto(), e.getCuerpo(), e.getCanal(), e.getEstado(),
                e.getDestinatario(), e.getCiudadanoId(), e.getProgramadaPara()
        );
    }

    private ComunicacionResponse toResponse(Comunicacion e) {
        return new ComunicacionResponse(
                e.getId(), e.getAsunto(), e.getCuerpo(), e.getCanal(), e.getEstado(),
                e.getDestinatario(), e.getCiudadanoId(), e.getProgramadaPara()
        );
    }

    @Test
    void listDelegatesToHandler() {
        Comunicacion e = buildComunicacion();
        when(handler.list()).thenReturn(List.of(e));
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        List<ComunicacionResponse> result = svc.list();

        assertThat(result).containsExactly(toResponse(e));
        verify(handler).list();
        verify(mapper).toResponse(e);
    }

    @Test
    void getDelegatesToHandler() {
        Comunicacion e = buildComunicacion();
        when(handler.get(e.getId())).thenReturn(e);
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        ComunicacionResponse result = svc.get(e.getId());

        assertThat(result).isEqualTo(toResponse(e));
        verify(handler).get(e.getId());
        verify(mapper).toResponse(e);
    }

    @Test
    void createDelegatesToHandler() {
        Comunicacion e = buildComunicacion();
        ComunicacionRequest request = toRequest(e);
        ComunicacionResponse expected = toResponse(e);

        when(mapper.toEntity(request)).thenReturn(e);
        when(handler.create(e)).thenReturn(e);
        when(mapper.toResponse(e)).thenReturn(expected);

        ComunicacionResponse result = svc.create(request);

        assertThat(result).isEqualTo(expected);
        verify(mapper).toEntity(request);
        verify(handler).create(e);
        verify(mapper).toResponse(e);
    }

    @Test
    void updateDelegatesToHandler() {
        Comunicacion e = buildComunicacion();
        ComunicacionRequest request = toRequest(e);

        when(handler.get(e.getId())).thenReturn(e);
        doNothing().when(mapper).updateEntity(e, request);
        when(handler.update(e.getId(), e)).thenReturn(e);
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        ComunicacionResponse result = svc.update(e.getId(), request);

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
