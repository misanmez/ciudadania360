package com.ciudadania360.gestionrolespermisos.application.service;

import com.ciudadania360.gestionrolespermisos.application.dto.usuariorol.UsuarioRolRequest;
import com.ciudadania360.gestionrolespermisos.application.dto.usuariorol.UsuarioRolResponse;
import com.ciudadania360.gestionrolespermisos.application.mapper.UsuarioRolMapper;
import com.ciudadania360.gestionrolespermisos.domain.entity.UsuarioRol;
import com.ciudadania360.gestionrolespermisos.domain.handler.UsuarioRolHandler;
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
class UsuarioRolServiceTest {

    @Mock
    private UsuarioRolHandler handler;

    @Mock
    private UsuarioRolMapper mapper;

    @InjectMocks
    private UsuarioRolService svc;

    private static final UUID FIXED_ID = UUID.fromString("3eeb3b08-3f4f-4c6e-991e-7e0e020e8bfa");
    private static final UUID FIXED_CIUDADANO_ID = UUID.fromString("11111111-1111-1111-1111-111111111111");
    private static final UUID FIXED_ROL_ID = UUID.fromString("22222222-2222-2222-2222-222222222222");

    private UsuarioRol buildUsuarioRol() {
        return UsuarioRol.builder()
                .id(FIXED_ID)
                .ciudadanoId(FIXED_CIUDADANO_ID)
                .rolId(FIXED_ROL_ID)
                .asignadoPor("admin")
                .fechaAsignacion(Instant.parse("2025-08-21T10:00:00Z"))
                .fechaCaducidad(Instant.parse("2025-08-21T11:00:00Z"))
                .origen("SYSTEM")
                .observaciones("Prueba")
                .version(1L)
                .build();
    }

    private UsuarioRolRequest toRequest(UsuarioRol e) {
        return new UsuarioRolRequest(
                e.getCiudadanoId(),
                e.getRolId(),
                e.getAsignadoPor(),
                e.getFechaAsignacion(),
                e.getFechaCaducidad(),
                e.getOrigen(),
                e.getObservaciones()
        );
    }

    private UsuarioRolResponse toResponse(UsuarioRol e) {
        return new UsuarioRolResponse(
                e.getId(),
                e.getCiudadanoId(),
                e.getRolId(),
                e.getAsignadoPor(),
                e.getFechaAsignacion(),
                e.getFechaCaducidad(),
                e.getOrigen(),
                e.getObservaciones()
        );
    }

    @Test
    void listDelegatesToHandler() {
        UsuarioRol e = buildUsuarioRol();
        when(handler.list()).thenReturn(List.of(e));
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        List<UsuarioRolResponse> result = svc.list();

        assertThat(result).containsExactly(toResponse(e));
        verify(handler).list();
        verify(mapper).toResponse(e);
    }

    @Test
    void getDelegatesToHandler() {
        UsuarioRol e = buildUsuarioRol();
        when(handler.get(e.getId())).thenReturn(e);
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        UsuarioRolResponse result = svc.get(e.getId());

        assertThat(result).isEqualTo(toResponse(e));
        verify(handler).get(e.getId());
        verify(mapper).toResponse(e);
    }

    @Test
    void createDelegatesToHandler() {
        UsuarioRol e = buildUsuarioRol();
        UsuarioRolRequest request = toRequest(e);

        when(mapper.toEntity(request)).thenReturn(e);
        when(handler.create(any(UsuarioRol.class))).thenAnswer(invocation -> {
            UsuarioRol arg = invocation.getArgument(0);
            arg.setId(FIXED_ID);
            return arg;
        });
        when(mapper.toResponse(any(UsuarioRol.class))).thenAnswer(invocation -> toResponse(invocation.getArgument(0)));

        UsuarioRolResponse result = svc.create(request);

        assertThat(result).isEqualTo(toResponse(e));
    }

    @Test
    void updateDelegatesToHandler() {
        UsuarioRol e = buildUsuarioRol();
        UsuarioRolRequest request = toRequest(e);

        when(handler.get(e.getId())).thenReturn(e);
        doNothing().when(mapper).updateEntity(any(UsuarioRol.class), eq(request));
        when(handler.update(eq(e.getId()), any(UsuarioRol.class))).thenReturn(e);
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        UsuarioRolResponse result = svc.update(e.getId(), request);

        assertThat(result).isEqualTo(toResponse(e));
        verify(handler).get(e.getId());
        verify(mapper).updateEntity(same(e), eq(request));
        verify(handler).update(eq(e.getId()), same(e));
        verify(mapper).toResponse(e);
    }

    @Test
    void deleteDelegatesToHandler() {
        UUID id = FIXED_ID;

        svc.delete(id);

        verify(handler).delete(id);
        verifyNoInteractions(mapper);
    }
}
