package com.ciudadania360.subsistemainformacion.application.service;

import com.ciudadania360.subsistemainformacion.application.dto.informacion.InformacionRequest;
import com.ciudadania360.subsistemainformacion.application.dto.informacion.InformacionResponse;
import com.ciudadania360.subsistemainformacion.application.mapper.InformacionMapper;
import com.ciudadania360.subsistemainformacion.domain.entity.Informacion;
import com.ciudadania360.subsistemainformacion.domain.handler.InformacionHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class InformacionServiceTest {

    @Mock
    private InformacionHandler handler;

    @Mock
    private InformacionMapper mapper;

    @InjectMocks
    private InformacionService svc;

    private Informacion buildEntity() {
        return Informacion.builder()
                .id(UUID.randomUUID())
                .titulo("Título de prueba")
                .contenido("Contenido de prueba")
                .etiquetas("tag1,tag2")
                .audiencia("Publico")
                .estadoPublicacion("Publicado")
                .propietario("Admin")
                .versionNumber(1)
                .fechaPublicacion(Instant.now())
                .version(1L)
                .build();
    }

    private InformacionRequest toRequest(Informacion e) {
        return new InformacionRequest(
                e.getTitulo(), e.getContenido(), e.getEtiquetas(),
                e.getAudiencia(), e.getEstadoPublicacion(),
                e.getPropietario(), e.getVersionNumber(),
                e.getFechaPublicacion()
        );
    }

    private InformacionResponse toResponse(Informacion e) {
        return new InformacionResponse(
                e.getId(), e.getTitulo(), e.getContenido(), e.getEtiquetas(),
                e.getAudiencia(), e.getEstadoPublicacion(),
                e.getPropietario(), e.getVersionNumber(),
                e.getFechaPublicacion()
        );
    }

    @Test
    void listDelegatesToHandler() {
        Informacion e = buildEntity();
        when(handler.list()).thenReturn(List.of(e));
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        List<InformacionResponse> result = svc.list();
        assertThat(result).containsExactly(toResponse(e));
        verify(handler).list();
        verify(mapper).toResponse(e);
    }

    @Test
    void getDelegatesToHandler() {
        Informacion e = buildEntity();
        when(handler.get(e.getId())).thenReturn(e);
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        InformacionResponse result = svc.get(e.getId());
        assertThat(result).isEqualTo(toResponse(e));
        verify(handler).get(e.getId());
        verify(mapper).toResponse(e);
    }

    @Test
    void createDelegatesToHandler() {
        Informacion e = Informacion.builder()
                .id(UUID.randomUUID())
                .titulo("Título 1")
                .contenido("Contenido 1")
                .etiquetas("tag1,tag2")
                .audiencia("General")
                .estadoPublicacion("Borrador")
                .propietario("Usuario")
                .versionNumber(1)
                .fechaPublicacion(Instant.now())
                .version(1L)
                .build();

        InformacionRequest request = toRequest(e);

        when(mapper.toEntity(request)).thenReturn(e);
        when(handler.create(any(Informacion.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(mapper.toResponse(any(Informacion.class))).thenAnswer(invocation -> toResponse(invocation.getArgument(0)));

        InformacionResponse result = svc.create(request);

        assertThat(result.getTitulo()).isEqualTo(e.getTitulo());
        assertThat(result.getContenido()).isEqualTo(e.getContenido());
        assertThat(result.getId()).isNotNull();

        verify(mapper).toEntity(request);
        verify(handler).create(any(Informacion.class));
        verify(mapper).toResponse(any(Informacion.class));
    }


    @Test
    void updateDelegatesToHandler() {
        Informacion e = buildEntity();
        InformacionRequest request = toRequest(e);
        when(handler.get(e.getId())).thenReturn(e);
        doNothing().when(mapper).updateEntity(e, request);
        when(handler.update(e.getId(), e)).thenReturn(e);
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        InformacionResponse result = svc.update(e.getId(), request);
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
