package com.ciudadania360.subsistemacomunicaciones.application.service;

import com.ciudadania360.subsistemacomunicaciones.application.dto.respuestaencuesta.RespuestaEncuestaRequest;
import com.ciudadania360.subsistemacomunicaciones.application.dto.respuestaencuesta.RespuestaEncuestaResponse;
import com.ciudadania360.subsistemacomunicaciones.application.mapper.RespuestaEncuestaMapper;
import com.ciudadania360.subsistemacomunicaciones.domain.entity.RespuestaEncuesta;
import com.ciudadania360.subsistemacomunicaciones.domain.handler.RespuestaEncuestaHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class RespuestaEncuestaServiceTest {

    @Mock
    private RespuestaEncuestaHandler handler;

    @Mock
    private RespuestaEncuestaMapper mapper;

    @InjectMocks
    private RespuestaEncuestaService svc;

    private RespuestaEncuesta buildRespuesta() {
        return RespuestaEncuesta.builder()
                .id(UUID.randomUUID())
                .encuestaId(UUID.randomUUID())
                .ciudadanoId(UUID.randomUUID())
                .respuestas("{\"1\":\"Sí\"}")
                .puntuacion(5)
                .comentarios("Comentario prueba")
                .fecha(Instant.now())
                .version(0L)
                .build();
    }

    private RespuestaEncuestaRequest toRequest(RespuestaEncuesta e) {
        return new RespuestaEncuestaRequest(
                e.getEncuestaId(), e.getCiudadanoId(), e.getRespuestas(),
                e.getPuntuacion(), e.getComentarios(), e.getFecha()
        );
    }

    private RespuestaEncuestaResponse toResponse(RespuestaEncuesta e) {
        return new RespuestaEncuestaResponse(
                e.getId(), e.getEncuestaId(), e.getCiudadanoId(), e.getRespuestas(),
                e.getPuntuacion(), e.getComentarios(), e.getFecha()
        );
    }

    @Test
    void listDelegatesToHandler() {
        RespuestaEncuesta e = buildRespuesta();
        when(handler.list()).thenReturn(List.of(e));
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        List<RespuestaEncuestaResponse> result = svc.list();

        assertThat(result).containsExactly(toResponse(e));
        verify(handler).list();
        verify(mapper).toResponse(e);
    }

    @Test
    void getDelegatesToHandler() {
        RespuestaEncuesta e = buildRespuesta();
        when(handler.get(e.getId())).thenReturn(e);
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        RespuestaEncuestaResponse result = svc.get(e.getId());

        assertThat(result).isEqualTo(toResponse(e));
        verify(handler).get(e.getId());
        verify(mapper).toResponse(e);
    }

    @Test
    void createDelegatesToHandler() {
        RespuestaEncuesta e = buildRespuesta();
        RespuestaEncuestaRequest request = toRequest(e);
        RespuestaEncuestaResponse expected = toResponse(e);

        when(mapper.toEntity(request)).thenReturn(e);
        when(handler.create(e)).thenReturn(e);
        when(mapper.toResponse(e)).thenReturn(expected);

        RespuestaEncuestaResponse result = svc.create(request);

        assertThat(result).isEqualTo(expected);
        verify(mapper).toEntity(request);
        verify(handler).create(e);
        verify(mapper).toResponse(e);
    }

    @Test
    void updateDelegatesToHandler() {
        RespuestaEncuesta e = buildRespuesta();
        RespuestaEncuestaRequest request = toRequest(e);

        when(handler.get(e.getId())).thenReturn(e);
        doNothing().when(mapper).updateEntity(e, request);
        when(handler.update(e.getId(), e)).thenReturn(e);
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        RespuestaEncuestaResponse result = svc.update(e.getId(), request);

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
