package com.ciudadania360.subsistemaciudadano.application.service;

import com.ciudadania360.subsistemaciudadano.application.dto.encuesta.EncuestaRequest;
import com.ciudadania360.subsistemaciudadano.application.dto.encuesta.EncuestaResponse;
import com.ciudadania360.subsistemaciudadano.application.mapper.EncuestaMapper;
import com.ciudadania360.subsistemaciudadano.application.validator.EncuestaValidator;
import com.ciudadania360.subsistemaciudadano.domain.entity.Encuesta;
import com.ciudadania360.subsistemaciudadano.domain.handler.EncuestaHandler;
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

    @Mock private EncuestaHandler handler;
    @Mock private EncuestaMapper mapper;
    @Mock private EncuestaValidator validator;
    @InjectMocks private EncuestaService svc;

    private Encuesta buildEncuesta() {
        return Encuesta.builder()
                .id(UUID.randomUUID())
                .solicitud(null)
                .tipo("SATISFACCION")
                .estado("PENDIENTE")
                .fechaEnvio(Instant.now())
                .fechaRespuesta(null)
                .respuestas("{}")
                .metadata("{\"nota\":\"test\"}")
                .version(1L)
                .build();
    }

    private EncuestaRequest toRequest(Encuesta e) {
        return new EncuestaRequest(
                e.getSolicitud() != null ? e.getSolicitud().getId() : null,
                e.getTipo(),
                e.getEstado(),
                e.getFechaEnvio(),
                e.getFechaRespuesta(),
                e.getRespuestas(),
                e.getMetadata()
        );
    }

    private EncuestaResponse toResponse(Encuesta e) {
        return new EncuestaResponse(
                e.getId(),
                e.getSolicitud() != null ? e.getSolicitud().getId() : null,
                e.getTipo(),
                e.getEstado(),
                e.getFechaEnvio(),
                e.getFechaRespuesta(),
                e.getRespuestas(),
                e.getMetadata(),
                e.getVersion()
        );
    }

    @Test void listDelegatesToHandler() {
        Encuesta e = buildEncuesta();
        when(handler.list()).thenReturn(List.of(e));
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        List<EncuestaResponse> result = svc.list();

        assertThat(result).containsExactly(toResponse(e));
        verify(handler).list();
        verify(mapper).toResponse(e);
    }

    @Test void getDelegatesToHandler() {
        Encuesta e = buildEncuesta();
        when(handler.get(e.getId())).thenReturn(e);
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        EncuestaResponse result = svc.get(e.getId());

        assertThat(result).isEqualTo(toResponse(e));
        verify(handler).get(e.getId());
        verify(mapper).toResponse(e);
    }

    @Test void createDelegatesToHandler() {
        Encuesta e = buildEncuesta();
        EncuestaRequest request = toRequest(e);
        EncuestaResponse expectedResponse = toResponse(e);

        when(mapper.toEntity(request)).thenReturn(e);
        when(handler.create(e)).thenReturn(e);
        when(mapper.toResponse(e)).thenReturn(expectedResponse);

        EncuestaResponse result = svc.create(request);

        assertThat(result).isEqualTo(expectedResponse);

        // Verificaciones
        verify(validator).validateCreate(request);
        verify(mapper).toEntity(request);
        verify(handler).create(e);
        verify(mapper).toResponse(e);
    }

    @Test void updateDelegatesToHandler() {
        Encuesta e = buildEncuesta();
        EncuestaRequest request = toRequest(e);

        when(handler.get(e.getId())).thenReturn(e);
        doNothing().when(mapper).updateEntity(any(Encuesta.class), eq(request));
        when(handler.update(eq(e.getId()), any(Encuesta.class))).thenReturn(e);
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        EncuestaResponse result = svc.update(e.getId(), request);

        assertThat(result).isEqualTo(toResponse(e));

        // Verificaciones
        verify(validator).validateUpdate(request);
        verify(handler).get(e.getId());
        verify(mapper).updateEntity(same(e), eq(request));
        verify(handler).update(eq(e.getId()), same(e));
        verify(mapper).toResponse(e);
    }

    @Test void deleteDelegatesToHandler() {
        UUID id = UUID.randomUUID();
        when(handler.exists(id)).thenReturn(true);

        svc.delete(id);

        // Verificaciones
        verify(validator).validateDelete(id, true);
        verify(handler).exists(id);
        verify(handler).delete(id);
        verifyNoInteractions(mapper);
    }
}
