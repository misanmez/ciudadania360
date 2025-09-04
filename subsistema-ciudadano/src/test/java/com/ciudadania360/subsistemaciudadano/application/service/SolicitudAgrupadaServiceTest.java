package com.ciudadania360.subsistemaciudadano.application.service;

import com.ciudadania360.subsistemaciudadano.application.dto.solicitudagrupada.SolicitudAgrupadaRequest;
import com.ciudadania360.subsistemaciudadano.application.dto.solicitudagrupada.SolicitudAgrupadaResponse;
import com.ciudadania360.subsistemaciudadano.application.mapper.SolicitudAgrupadaMapper;
import com.ciudadania360.subsistemaciudadano.application.validator.SolicitudAgrupadaValidator;
import com.ciudadania360.subsistemaciudadano.domain.entity.Solicitud;
import com.ciudadania360.subsistemaciudadano.domain.entity.SolicitudAgrupada;
import com.ciudadania360.subsistemaciudadano.domain.handler.SolicitudAgrupadaHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class SolicitudAgrupadaServiceTest {

    @Mock private SolicitudAgrupadaHandler handler;
    @Mock private SolicitudAgrupadaMapper mapper;
    @Mock private SolicitudAgrupadaValidator validator; // <-- agregado
    @InjectMocks private SolicitudAgrupadaService svc;

    private SolicitudAgrupada buildSolicitudAgrupada() {
        return SolicitudAgrupada.builder()
                .id(UUID.randomUUID())
                .solicitudPadre(Solicitud.builder().id(UUID.randomUUID()).build())
                .solicitudHija(Solicitud.builder().id(UUID.randomUUID()).build())
                .metadata("{\"info\":\"test\"}")
                .version(1L)
                .build();
    }

    private SolicitudAgrupadaRequest toRequest(SolicitudAgrupada e) {
        return new SolicitudAgrupadaRequest(
                e.getSolicitudPadre() != null ? e.getSolicitudPadre().getId() : null,
                e.getSolicitudHija() != null ? e.getSolicitudHija().getId() : null,
                e.getMetadata()
        );
    }

    private SolicitudAgrupadaResponse toResponse(SolicitudAgrupada e) {
        return new SolicitudAgrupadaResponse(
                e.getId(),
                e.getSolicitudPadre() != null ? e.getSolicitudPadre().getId() : null,
                e.getSolicitudHija() != null ? e.getSolicitudHija().getId() : null,
                e.getMetadata(),
                e.getVersion()
        );
    }

    @Test void listDelegatesToHandler() {
        SolicitudAgrupada e = buildSolicitudAgrupada();
        when(handler.list()).thenReturn(List.of(e));
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        List<SolicitudAgrupadaResponse> result = svc.list();

        assertThat(result).containsExactly(toResponse(e));
        verify(handler).list();
        verify(mapper).toResponse(e);
    }

    @Test void getDelegatesToHandler() {
        SolicitudAgrupada e = buildSolicitudAgrupada();
        when(handler.get(e.getId())).thenReturn(e);
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        SolicitudAgrupadaResponse result = svc.get(e.getId());

        assertThat(result).isEqualTo(toResponse(e));
        verify(handler).get(e.getId());
        verify(mapper).toResponse(e);
    }

    @Test void createDelegatesToHandler() {
        SolicitudAgrupada e = buildSolicitudAgrupada();
        SolicitudAgrupadaRequest request = toRequest(e);
        SolicitudAgrupadaResponse expectedResponse = toResponse(e);

        doNothing().when(validator).validateCreate(request); // <-- stub del validator
        when(mapper.toEntity(request)).thenReturn(e);
        when(handler.create(e)).thenReturn(e);
        when(mapper.toResponse(e)).thenReturn(expectedResponse);

        SolicitudAgrupadaResponse result = svc.create(request);

        assertThat(result).isEqualTo(expectedResponse);
        verify(validator).validateCreate(request);
        verify(mapper).toEntity(request);
        verify(handler).create(e);
        verify(mapper).toResponse(e);
    }

    @Test void updateDelegatesToHandler() {
        SolicitudAgrupada e = buildSolicitudAgrupada();
        SolicitudAgrupadaRequest request = toRequest(e);

        doNothing().when(validator).validateUpdate(request); // <-- stub del validator
        when(handler.get(e.getId())).thenReturn(e);
        doNothing().when(mapper).updateEntity(any(SolicitudAgrupada.class), eq(request));
        when(handler.update(eq(e.getId()), any(SolicitudAgrupada.class))).thenReturn(e);
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        SolicitudAgrupadaResponse result = svc.update(e.getId(), request);

        assertThat(result).isEqualTo(toResponse(e));
        verify(validator).validateUpdate(request);
        verify(handler).get(e.getId());
        verify(mapper).updateEntity(same(e), eq(request));
        verify(handler).update(eq(e.getId()), same(e));
        verify(mapper).toResponse(e);
    }

    @Test void deleteDelegatesToHandler() {
        SolicitudAgrupada e = buildSolicitudAgrupada();
        when(handler.get(e.getId())).thenReturn(e);
        doNothing().when(validator).validateDelete(e); // <-- stub del validator

        svc.delete(e.getId());

        verify(handler).get(e.getId());
        verify(validator).validateDelete(e);
        verify(handler).delete(e.getId());
    }
}
