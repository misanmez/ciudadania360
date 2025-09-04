package com.ciudadania360.subsistemaciudadano.controller;

import com.ciudadania360.subsistemaciudadano.application.dto.consentimiento.ConsentimientoRequest;
import com.ciudadania360.subsistemaciudadano.application.dto.consentimiento.ConsentimientoResponse;
import com.ciudadania360.subsistemaciudadano.application.service.ConsentimientoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsentimientoControllerTest {

    @Mock
    private ConsentimientoService service;

    @InjectMocks
    private ConsentimientoController controller;

    private ConsentimientoResponse buildResponse() {
        return ConsentimientoResponse.builder()
                .id(UUID.randomUUID())
                .ciudadanoId(UUID.randomUUID())
                .tipo("LOPD")
                .otorgado(true)
                .fechaOtorgamiento(Instant.now())
                .version(1L)
                .build();
    }

    private ConsentimientoRequest buildRequest() {
        return ConsentimientoRequest.builder()
                .ciudadanoId(UUID.randomUUID())
                .tipo("GDPR")
                .otorgado(false)
                .fechaOtorgamiento(Instant.now())
                .build();
    }

    @Test
    void listReturnsOk() {
        ConsentimientoResponse resp = buildResponse();
        when(service.list()).thenReturn(List.of(resp));

        ResponseEntity<List<ConsentimientoResponse>> result = controller.list();

        assertThat(result.getBody()).containsExactly(resp);
        verify(service).list();
    }

    @Test
    void getReturnsOk() {
        ConsentimientoResponse resp = buildResponse();
        when(service.get(resp.getId())).thenReturn(resp);

        ResponseEntity<ConsentimientoResponse> result = controller.get(resp.getId());

        assertThat(result.getBody()).isEqualTo(resp);
        verify(service).get(resp.getId());
    }

    @Test
    void createReturnsOk() {
        ConsentimientoResponse resp = buildResponse();
        ConsentimientoRequest req = buildRequest();
        when(service.create(req)).thenReturn(resp);

        ResponseEntity<ConsentimientoResponse> result = controller.create(req);

        assertThat(result.getBody()).isEqualTo(resp);
        verify(service).create(req);
    }

    @Test
    void updateReturnsOk() {
        ConsentimientoResponse resp = buildResponse();
        ConsentimientoRequest req = buildRequest();
        when(service.update(resp.getId(), req)).thenReturn(resp);

        ResponseEntity<ConsentimientoResponse> result = controller.update(resp.getId(), req);

        assertThat(result.getBody()).isEqualTo(resp);
        verify(service).update(resp.getId(), req);
    }

    @Test
    void deleteReturnsNoContent() {
        UUID id = UUID.randomUUID();

        ResponseEntity<Void> result = controller.delete(id);

        assertThat(result.getStatusCode().is2xxSuccessful()).isTrue();
        verify(service).delete(id);
    }
}
