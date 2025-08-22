package com.ciudadania360.subsistematramitacion.application.service;

import com.ciudadania360.subsistematramitacion.domain.entity.Carpeta;
import com.ciudadania360.subsistematramitacion.domain.handler.CarpetaHandler;
import com.ciudadania360.subsistematramitacion.application.mapper.CarpetaMapper;
import com.ciudadania360.subsistematramitacion.application.dto.carpeta.CarpetaRequest;
import com.ciudadania360.subsistematramitacion.application.dto.carpeta.CarpetaResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class CarpetaServiceTest {

    @Mock private CarpetaHandler handler;
    @Mock private CarpetaMapper mapper;
    @InjectMocks private CarpetaService svc;

    private Carpeta buildEntity() {
        return Carpeta.builder()
                .id(UUID.randomUUID())
                .nombre("Carpeta 1")
                .descripcion("Descripción")
                .tipo("Tipo A")
                .ruta("/ruta/1")
                .numeroExpediente("EXP-001")
                .estado("Activo")
                .permisos("{}")
                .solicitudId(UUID.randomUUID())
                .version(1L)
                .build();
    }

    private CarpetaRequest toRequest(Carpeta e) {
        return new CarpetaRequest(
                e.getSolicitudId(), e.getNombre(), e.getDescripcion(), e.getTipo(),
                e.getRuta(), e.getPermisos(), e.getNumeroExpediente(), e.getEstado()
        );
    }

    private CarpetaResponse toResponse(Carpeta e) {
        return new CarpetaResponse(
                e.getId(), e.getSolicitudId(), e.getNombre(), e.getDescripcion(), e.getTipo(),
                e.getRuta(), e.getPermisos(), e.getNumeroExpediente(), e.getEstado()
        );
    }

    @Test void listDelegatesToHandler() {
        Carpeta e = buildEntity();
        when(handler.list()).thenReturn(List.of(e));
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        List<CarpetaResponse> result = svc.list();

        assertThat(result).containsExactly(toResponse(e));
        verify(handler).list();
        verify(mapper).toResponse(e);
    }

    @Test void getDelegatesToHandler() {
        Carpeta e = buildEntity();
        when(handler.get(e.getId())).thenReturn(e);
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        CarpetaResponse result = svc.get(e.getId());

        assertThat(result).isEqualTo(toResponse(e));
        verify(handler).get(e.getId());
        verify(mapper).toResponse(e);
    }

    @Test
    void createDelegatesToHandler() {
        Carpeta e = buildEntity();
        CarpetaRequest request = toRequest(e);

        when(mapper.toEntity(request)).thenReturn(e);
        when(handler.create(any(Carpeta.class))).thenReturn(e);
        when(mapper.toResponse(any(Carpeta.class))).thenReturn(toResponse(e));

        CarpetaResponse result = svc.create(request);

        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(toResponse(e));

        verify(mapper).toEntity(request);
        verify(handler).create(any(Carpeta.class));
        verify(mapper).toResponse(any(Carpeta.class));
    }



    @Test void updateDelegatesToHandler() {
        Carpeta e = buildEntity();
        CarpetaRequest request = toRequest(e);

        when(handler.get(e.getId())).thenReturn(e);
        doNothing().when(mapper).updateEntity(e, request);
        when(handler.update(eq(e.getId()), same(e))).thenReturn(e);
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        CarpetaResponse result = svc.update(e.getId(), request);

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
