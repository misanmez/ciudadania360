package com.ciudadania360.subsistemacomunicaciones.application.service;

import com.ciudadania360.subsistemacomunicaciones.application.dto.encuesta.EncuestaRequest;
import com.ciudadania360.subsistemacomunicaciones.application.dto.encuesta.EncuestaResponse;
import com.ciudadania360.subsistemacomunicaciones.application.mapper.EncuestaMapper;
import com.ciudadania360.subsistemacomunicaciones.domain.entity.Encuesta;
import com.ciudadania360.subsistemacomunicaciones.domain.handler.EncuestaHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class EncuestaServiceTest {

    @Mock
    private EncuestaHandler handler;

    @Mock
    private EncuestaMapper mapper;

    @InjectMocks
    private EncuestaService svc;

    private Encuesta buildEncuesta() {
        return Encuesta.builder()
                .id(UUID.randomUUID())
                .titulo("Encuesta prueba")
                .descripcion("Descripción")
                .preguntas("[{\"pregunta\":\"¿Sí o no?\"}]")
                .estado("ACTIVA")
                .audiencia("GENERAL")
                .fechaInicio(Instant.now())
                .fechaFin(Instant.now().plusSeconds(3600))
                .vinculadaSolicitudId(UUID.randomUUID())
                .version(0L)
                .build();
    }

    private EncuestaRequest toRequest(Encuesta e) {
        return new EncuestaRequest(
                e.getTitulo(), e.getDescripcion(), e.getPreguntas(),
                e.getEstado(), e.getAudiencia(), e.getFechaInicio(),
                e.getFechaFin(), e.getVinculadaSolicitudId()
        );
    }

    private EncuestaResponse toResponse(Encuesta e) {
        return new EncuestaResponse(
                e.getId(), e.getTitulo(), e.getDescripcion(), e.getPreguntas(),
                e.getEstado(), e.getAudiencia(), e.getFechaInicio(),
                e.getFechaFin(), e.getVinculadaSolicitudId()
        );
    }

    @Test
    void listDelegatesToHandler() {
        Encuesta e = buildEncuesta();
        when(handler.list()).thenReturn(List.of(e));
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        List<EncuestaResponse> result = svc.list();

        assertThat(result).containsExactly(toResponse(e));
        verify(handler).list();
        verify(mapper).toResponse(e);
    }

    @Test
    void getDelegatesToHandler() {
        Encuesta e = buildEncuesta();
        when(handler.get(e.getId())).thenReturn(e);
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        EncuestaResponse result = svc.get(e.getId());

        assertThat(result).isEqualTo(toResponse(e));
        verify(handler).get(e.getId());
        verify(mapper).toResponse(e);
    }

    @Test
    void createDelegatesToHandler() {
        Encuesta e = buildEncuesta();
        EncuestaRequest request = toRequest(e);
        EncuestaResponse expected = toResponse(e);

        when(mapper.toEntity(request)).thenReturn(e);
        when(handler.create(e)).thenReturn(e);
        when(mapper.toResponse(e)).thenReturn(expected);

        EncuestaResponse result = svc.create(request);

        assertThat(result).isEqualTo(expected);
        verify(mapper).toEntity(request);
        verify(handler).create(e);
        verify(mapper).toResponse(e);
    }

    @Test
    void updateDelegatesToHandler() {
        Encuesta e = buildEncuesta();
        EncuestaRequest request = toRequest(e);

        when(handler.get(e.getId())).thenReturn(e);
        doNothing().when(mapper).updateEntity(e, request);
        when(handler.update(e.getId(), e)).thenReturn(e);
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        EncuestaResponse result = svc.update(e.getId(), request);

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
