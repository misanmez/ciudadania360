package com.ciudadania360.subsistemaciudadano.application.service;

import com.ciudadania360.subsistemaciudadano.application.dto.consentimiento.ConsentimientoRequest;
import com.ciudadania360.subsistemaciudadano.application.dto.consentimiento.ConsentimientoResponse;
import com.ciudadania360.subsistemaciudadano.application.mapper.ConsentimientoMapper;
import com.ciudadania360.subsistemaciudadano.domain.entity.Consentimiento;
import com.ciudadania360.subsistemaciudadano.domain.handler.ConsentimientoHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsentimientoServiceTest {

    @Mock
    private ConsentimientoHandler handler;

    @Mock
    private ConsentimientoMapper mapper;

    @InjectMocks
    private ConsentimientoService svc;

    private Consentimiento buildConsentimiento() {
        UUID id = UUID.fromString("00000000-0000-0000-0000-000000000001");
        UUID ciudadanoId = UUID.fromString("00000-0000-0000-0000-000000000002");
        return Consentimiento.builder()
                .id(id)
                .ciudadanoId(ciudadanoId)
                .tipo("LOPD")
                .otorgado(true)
                .build();
    }

    private ConsentimientoRequest toRequest(Consentimiento e) {
        return new ConsentimientoRequest(
                e.getCiudadanoId(), e.getTipo(), e.getOtorgado()
        );
    }

    private ConsentimientoResponse toResponse(Consentimiento e) {
        return new ConsentimientoResponse(
                e.getId(), e.getCiudadanoId(), e.getTipo(), e.getOtorgado()
        );
    }

    @Test
    void listDelegatesToHandler() {
        Consentimiento e = buildConsentimiento();
        ConsentimientoResponse expectedResponse = toResponse(e);

        when(handler.list()).thenReturn(List.of(e));
        when(mapper.toResponse(e)).thenReturn(expectedResponse);

        List<ConsentimientoResponse> result = svc.list();

        assertThat(result).containsExactly(expectedResponse);
        verify(handler).list();
        verify(mapper).toResponse(e);
    }

    @Test
    void getDelegatesToHandler() {
        Consentimiento e = buildConsentimiento();
        ConsentimientoResponse expectedResponse = toResponse(e);

        when(handler.get(e.getId())).thenReturn(e);
        when(mapper.toResponse(e)).thenReturn(expectedResponse);

        ConsentimientoResponse result = svc.get(e.getId());

        assertThat(result).isEqualTo(expectedResponse);
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
        verify(mapper).toEntity(request);
        verify(handler).create(e);
        verify(mapper).toResponse(e);
    }

    @Test
    void updateDelegatesToHandler() {
        Consentimiento original = buildConsentimiento();
        ConsentimientoRequest request = new ConsentimientoRequest(original.getCiudadanoId(), "GDPR", false);

        Consentimiento updated = Consentimiento.builder()
                .id(original.getId())
                .ciudadanoId(original.getCiudadanoId())
                .tipo("GDPR")
                .otorgado(false)
                .build();

        ConsentimientoResponse expectedResponse = toResponse(updated);

        when(handler.get(original.getId())).thenReturn(original);
        when(handler.update(eq(original.getId()), any())).thenReturn(updated);
        when(mapper.toResponse(updated)).thenReturn(expectedResponse);

        ConsentimientoResponse result = svc.update(original.getId(), request);

        assertThat(result.getTipo()).isEqualTo("GDPR");
        assertThat(result.getOtorgado()).isFalse();
        verify(handler).get(original.getId());
        verify(handler).update(eq(original.getId()), any(Consentimiento.class));
        verify(mapper).toResponse(updated);
    }

    @Test
    void deleteDelegatesToHandler() {
        UUID id = UUID.fromString("00000000-0000-0000-0000-000000000003");

        svc.delete(id);

        verify(handler).delete(id);
        verifyNoInteractions(mapper);
    }
}
