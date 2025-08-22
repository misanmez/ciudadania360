package com.ciudadania360.subsistemavideoconferencia.application.service;

import com.ciudadania360.subsistemavideoconferencia.application.dto.dispositivo.DispositivoRequest;
import com.ciudadania360.subsistemavideoconferencia.application.dto.dispositivo.DispositivoResponse;
import com.ciudadania360.subsistemavideoconferencia.application.mapper.DispositivoMapper;
import com.ciudadania360.subsistemavideoconferencia.domain.entity.Dispositivo;
import com.ciudadania360.subsistemavideoconferencia.domain.handler.DispositivoHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class DispositivoServiceTest {

    @Mock private DispositivoHandler handler;
    @Mock private DispositivoMapper mapper;
    @InjectMocks private DispositivoService svc;

    private static final UUID FIXED_ID = UUID.fromString("11111111-1111-1111-1111-111111111111");
    private static final Instant FIXED_INSTANT = Instant.parse("2025-08-22T09:02:58.214242400Z");

    private Dispositivo buildEntity() {
        return Dispositivo.builder()
                .id(FIXED_ID)
                .ciudadanoId(UUID.randomUUID())
                .tipo("Laptop")
                .sistemaOperativo("Windows 11")
                .navegador("Chrome")
                .capacidadVideo("1080p")
                .microfono(true)
                .camara(true)
                .red("WiFi")
                .pruebaRealizada(true)
                .ultimoCheck(FIXED_INSTANT)
                .version(1L)
                .build();
    }

    private DispositivoRequest toRequest(Dispositivo e) {
        return new DispositivoRequest(
                e.getCiudadanoId(),
                e.getTipo(),
                e.getSistemaOperativo(),
                e.getNavegador(),
                e.getCapacidadVideo(),
                e.getMicrofono(),
                e.getCamara(),
                e.getRed(),
                e.getPruebaRealizada(),
                e.getUltimoCheck()
        );
    }

    private DispositivoResponse toResponse(Dispositivo e) {
        return new DispositivoResponse(
                e.getId(),
                e.getCiudadanoId(),
                e.getTipo(),
                e.getSistemaOperativo(),
                e.getNavegador(),
                e.getCapacidadVideo(),
                e.getMicrofono(),
                e.getCamara(),
                e.getRed(),
                e.getPruebaRealizada(),
                e.getUltimoCheck()
        );
    }

    @Test void listDelegatesToHandler() {
        Dispositivo e = buildEntity();
        when(handler.list()).thenReturn(List.of(e));
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        List<DispositivoResponse> result = svc.list();

        assertThat(result).containsExactly(toResponse(e));
        verify(handler).list();
        verify(mapper).toResponse(e);
    }

    @Test void getDelegatesToHandler() {
        Dispositivo e = buildEntity();
        when(handler.get(e.getId())).thenReturn(e);
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        DispositivoResponse result = svc.get(e.getId());

        assertThat(result).isEqualTo(toResponse(e));
        verify(handler).get(e.getId());
        verify(mapper).toResponse(e);
    }

    @Test void createDelegatesToHandler() {
        Dispositivo e = buildEntity();
        DispositivoRequest request = toRequest(e);

        when(mapper.toEntity(request)).thenReturn(e);
        when(handler.create(any(Dispositivo.class))).thenReturn(e);
        when(mapper.toResponse(any(Dispositivo.class))).thenReturn(toResponse(e));

        DispositivoResponse result = svc.create(request);

        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(toResponse(e));

        verify(mapper).toEntity(request);
        verify(handler).create(any(Dispositivo.class));
        verify(mapper).toResponse(any(Dispositivo.class));
    }

    @Test void updateDelegatesToHandler() {
        Dispositivo e = buildEntity();
        DispositivoRequest request = toRequest(e);

        when(handler.get(e.getId())).thenReturn(e);
        doNothing().when(mapper).updateEntity(e, request);
        when(handler.update(eq(e.getId()), same(e))).thenReturn(e);
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        DispositivoResponse result = svc.update(e.getId(), request);

        assertThat(result).isEqualTo(toResponse(e));
        verify(handler).get(e.getId());
        verify(mapper).updateEntity(e, request);
        verify(handler).update(e.getId(), e);
        verify(mapper).toResponse(e);
    }

    @Test void deleteDelegatesToHandler() {
        UUID id = UUID.randomUUID();
        svc.delete(id);
        verify(handler).delete(id);
        verifyNoInteractions(mapper);
    }
}
