package com.ciudadania360.subsistemaciudadano.application.service;

import com.ciudadania360.shared.application.dto.consentimiento.ConsentimientoRequest;
import com.ciudadania360.shared.application.dto.consentimiento.ConsentimientoResponse;
import com.ciudadania360.subsistemaciudadano.application.mapper.ConsentimientoMapper;
import com.ciudadania360.subsistemaciudadano.application.validator.ConsentimientoValidator;
import com.ciudadania360.subsistemaciudadano.domain.entity.Ciudadano;
import com.ciudadania360.subsistemaciudadano.domain.entity.Consentimiento;
import com.ciudadania360.subsistemaciudadano.domain.handler.ConsentimientoHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsentimientoServiceTest {

    @Mock
    private ConsentimientoHandler handler;

    @Mock
    private ConsentimientoMapper mapper;

    @Mock
    private ConsentimientoValidator validator;

    @InjectMocks
    private ConsentimientoService svc;

    private Consentimiento buildConsentimiento() {
        UUID id = UUID.fromString("00000000-0000-0000-0000-000000000001");
        Ciudadano ciudadano = Ciudadano.builder().id(UUID.fromString("00000000-0000-0000-0000-000000000002")).build();
        return Consentimiento.builder()
                .id(id)
                .ciudadano(ciudadano)
                .tipo("LOPD")
                .otorgado(true)
                .fechaOtorgamiento(java.util.Date.from(Instant.parse("2025-09-01T10:00:00Z")))
                .version(1L)
                .build();
    }

    private ConsentimientoRequest toRequest(Consentimiento e) {
        return ConsentimientoRequest.builder()
                .ciudadanoId(e.getCiudadano().getId())
                .tipo(e.getTipo())
                .otorgado(e.getOtorgado())
                .fechaOtorgamiento(e.getFechaOtorgamiento().toInstant())
                .build();
    }

    private ConsentimientoResponse toResponse(Consentimiento e) {
        return ConsentimientoResponse.builder()
                .id(e.getId())
                .ciudadanoId(e.getCiudadano().getId())
                .tipo(e.getTipo())
                .otorgado(e.getOtorgado())
                .fechaOtorgamiento(e.getFechaOtorgamiento().toInstant())
                .version(e.getVersion())
                .build();
    }

    @Test
    void listDelegatesToHandler() {
        Consentimiento e = buildConsentimiento();
        when(handler.list()).thenReturn(List.of(e));
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        List<ConsentimientoResponse> result = svc.list();

        assertThat(result).containsExactly(toResponse(e));
        verify(handler).list();
        verify(mapper).toResponse(e);
    }

    @Test
    void getDelegatesToHandler() {
        Consentimiento e = buildConsentimiento();
        when(handler.get(e.getId())).thenReturn(e);
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        ConsentimientoResponse result = svc.get(e.getId());

        assertThat(result).isEqualTo(toResponse(e));
        verify(handler).get(e.getId());
        verify(mapper).toResponse(e);
    }

    @Test
    void createDelegatesToHandler() {
        Consentimiento e = buildConsentimiento();
        ConsentimientoRequest request = toRequest(e);
        ConsentimientoResponse expectedResponse = toResponse(e);

        when(mapper.toEntity(request)).thenReturn(e);
        when(handler.create(e)).thenReturn(e);
        when(mapper.toResponse(e)).thenReturn(expectedResponse);

        ConsentimientoResponse result = svc.create(request);

        assertThat(result).isEqualTo(expectedResponse);
        verify(validator).validate(request, true);
        verify(mapper).toEntity(request);
        verify(handler).create(e);
        verify(mapper).toResponse(e);
    }

    @Test
    void updateDelegatesToHandler() {
        Consentimiento original = buildConsentimiento();
        ConsentimientoRequest request = new ConsentimientoRequest(
                original.getCiudadano().getId(), "GDPR", false, Instant.parse("2025-09-02T12:00:00Z")
        );

        Consentimiento updated = Consentimiento.builder()
                .id(original.getId())
                .ciudadano(original.getCiudadano())
                .tipo("GDPR")
                .otorgado(false)
                .fechaOtorgamiento(java.util.Date.from(request.getFechaOtorgamiento()))
                .version(1L)
                .build();

        when(handler.get(original.getId())).thenReturn(original);
        doNothing().when(mapper).updateEntity(same(original), eq(request));
        when(handler.update(eq(original.getId()), same(original))).thenReturn(updated);
        when(mapper.toResponse(updated)).thenReturn(toResponse(updated));

        ConsentimientoResponse result = svc.update(original.getId(), request);

        assertThat(result.getTipo()).isEqualTo("GDPR");
        assertThat(result.getOtorgado()).isFalse();
        assertThat(result.getFechaOtorgamiento()).isEqualTo(request.getFechaOtorgamiento());

        verify(validator).validate(request, false);
        verify(handler).get(original.getId());
        verify(mapper).updateEntity(same(original), eq(request));
        verify(handler).update(eq(original.getId()), same(original));
        verify(mapper).toResponse(updated);
    }

    @Test
    void deleteDelegatesToHandler() {
        UUID id = UUID.fromString("00000000-0000-0000-0000-000000000003");

        svc.delete(id);

        verify(handler).delete(id);
        verifyNoInteractions(mapper);
        verifyNoInteractions(validator);
    }
}
