package com.ciudadania360.subsistemainformacion.application.service;

import com.ciudadania360.subsistemainformacion.application.dto.sugerencia.SugerenciaRequest;
import com.ciudadania360.subsistemainformacion.application.dto.sugerencia.SugerenciaResponse;
import com.ciudadania360.subsistemainformacion.application.mapper.SugerenciaMapper;
import com.ciudadania360.subsistemainformacion.domain.entity.Sugerencia;
import com.ciudadania360.subsistemainformacion.domain.handler.SugerenciaHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class SugerenciaServiceTest {

    @Mock
    private SugerenciaHandler handler;

    @Mock
    private SugerenciaMapper mapper;

    @InjectMocks
    private SugerenciaService svc;

    private Sugerencia buildEntity() {
        return Sugerencia.builder()
                .id(UUID.randomUUID())
                .titulo("Título")
                .descripcion("Descripción")
                .ciudadanoId(UUID.randomUUID())
                .estado("Pendiente")
                .prioridad("Alta")
                .areaResponsable("TI")
                .fecha(Instant.now())
                .version(1L)
                .build();
    }

    private SugerenciaRequest toRequest(Sugerencia e) {
        return new SugerenciaRequest(e.getTitulo(), e.getDescripcion(), e.getCiudadanoId(), e.getEstado(),
                e.getPrioridad(), e.getAreaResponsable(), e.getFecha());
    }

    private SugerenciaResponse toResponse(Sugerencia e) {
        return new SugerenciaResponse(e.getId(), e.getTitulo(), e.getDescripcion(), e.getCiudadanoId(),
                e.getEstado(), e.getPrioridad(), e.getAreaResponsable(), e.getFecha());
    }

    @Test
    void listDelegatesToHandler() {
        Sugerencia e = buildEntity();
        when(handler.list()).thenReturn(List.of(e));
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        List<SugerenciaResponse> result = svc.list();

        assertThat(result).containsExactly(toResponse(e));
        verify(handler).list();
        verify(mapper).toResponse(e);
    }

    @Test
    void getDelegatesToHandler() {
        Sugerencia e = buildEntity();
        when(handler.get(e.getId())).thenReturn(e);
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        SugerenciaResponse result = svc.get(e.getId());

        assertThat(result).isEqualTo(toResponse(e));
        verify(handler).get(e.getId());
        verify(mapper).toResponse(e);
    }

    @Test
    void createDelegatesToHandler() {
        Sugerencia e = Sugerencia.builder()
                .id(UUID.randomUUID())
                .titulo("Título sugerencia")
                .descripcion("Descripción de la sugerencia")
                .ciudadanoId(UUID.randomUUID())
                .estado("Pendiente")
                .prioridad("Alta")
                .areaResponsable("Área X")
                .fecha(Instant.now())
                .version(1L)
                .build();

        SugerenciaRequest request = new SugerenciaRequest(
                e.getTitulo(), e.getDescripcion(), e.getCiudadanoId(),
                e.getEstado(), e.getPrioridad(), e.getAreaResponsable(), e.getFecha()
        );

        when(mapper.toEntity(request)).thenReturn(e);
        when(handler.create(any(Sugerencia.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(mapper.toResponse(any(Sugerencia.class))).thenAnswer(invocation -> toResponse(invocation.getArgument(0)));


        SugerenciaResponse result = svc.create(request);

        assertThat(result.getTitulo()).isEqualTo(e.getTitulo());
        assertThat(result.getDescripcion()).isEqualTo(e.getDescripcion());
        assertThat(result.getId()).isNotNull();

        verify(mapper).toEntity(request);
        verify(handler).create(any(Sugerencia.class));
        verify(mapper).toResponse(any(Sugerencia.class));
    }


    @Test
    void updateDelegatesToHandler() {
        Sugerencia e = buildEntity();
        SugerenciaRequest request = toRequest(e);

        when(handler.get(e.getId())).thenReturn(e);
        doNothing().when(mapper).updateEntity(e, request);
        when(handler.update(eq(e.getId()), same(e))).thenReturn(e);
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        SugerenciaResponse result = svc.update(e.getId(), request);

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
