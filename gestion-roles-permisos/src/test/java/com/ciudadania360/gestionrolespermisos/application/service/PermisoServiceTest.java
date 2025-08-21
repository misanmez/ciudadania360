package com.ciudadania360.gestionrolespermisos.application.service;

import com.ciudadania360.gestionrolespermisos.application.dto.permiso.PermisoRequest;
import com.ciudadania360.gestionrolespermisos.application.dto.permiso.PermisoResponse;
import com.ciudadania360.gestionrolespermisos.application.mapper.PermisoMapper;
import com.ciudadania360.gestionrolespermisos.domain.entity.Permiso;
import com.ciudadania360.gestionrolespermisos.domain.handler.PermisoHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PermisoServiceTest {

    @Mock
    private PermisoHandler handler;

    @Mock
    private PermisoMapper mapper;

    @InjectMocks
    private PermisoService svc;

    private static final UUID FIXED_ID = UUID.fromString("cfd0b998-0f99-430b-9df3-157eae3e6c4a");

    private Permiso buildPermiso() {
        return Permiso.builder()
                .id(FIXED_ID)
                .codigo("PERM001")
                .nombre("Permiso Test")
                .descripcion("Descripción de prueba")
                .scope("GLOBAL")
                .recurso("RECURSO_TEST")
                .accion("ACCION_TEST")
                .activo(true)
                .build();
    }

    private PermisoRequest toRequest(Permiso e) {
        return new PermisoRequest(
                e.getCodigo(),
                e.getNombre(),
                e.getDescripcion(),
                e.getScope(),
                e.getRecurso(),
                e.getAccion(),
                e.getActivo()
        );
    }

    private PermisoResponse toResponse(Permiso e) {
        return new PermisoResponse(
                e.getId(),
                e.getCodigo(),
                e.getNombre(),
                e.getDescripcion(),
                e.getScope(),
                e.getRecurso(),
                e.getAccion(),
                e.getActivo()
        );
    }

    @Test
    void listDelegatesToHandler() {
        Permiso e = buildPermiso();
        when(handler.list()).thenReturn(List.of(e));
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        List<PermisoResponse> result = svc.list();

        assertThat(result).containsExactly(toResponse(e));
        verify(handler).list();
        verify(mapper).toResponse(e);
    }

    @Test
    void getDelegatesToHandler() {
        Permiso e = buildPermiso();
        when(handler.get(e.getId())).thenReturn(e);
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        PermisoResponse result = svc.get(e.getId());

        assertThat(result).isEqualTo(toResponse(e));
        verify(handler).get(e.getId());
        verify(mapper).toResponse(e);
    }

    @Test
    void createDelegatesToHandler() {
        Permiso e = buildPermiso();
        PermisoRequest request = toRequest(e);

        when(mapper.toEntity(request)).thenReturn(e);

        // Forzamos que el handler devuelva el mismo Permiso con FIXED_ID
        when(handler.create(any(Permiso.class))).thenAnswer(invocation -> {
            Permiso arg = invocation.getArgument(0);
            arg.setId(FIXED_ID);
            return arg;
        });

        when(mapper.toResponse(any(Permiso.class))).thenAnswer(invocation -> toResponse(invocation.getArgument(0)));

        PermisoResponse result = svc.create(request);

        assertThat(result).isEqualTo(toResponse(e));
        verify(mapper).toEntity(request);
        verify(handler).create(any(Permiso.class));
        verify(mapper).toResponse(any(Permiso.class));
    }

    @Test
    void updateDelegatesToHandler() {
        Permiso e = buildPermiso();
        PermisoRequest request = toRequest(e);

        when(handler.get(e.getId())).thenReturn(e);
        doNothing().when(mapper).updateEntity(any(Permiso.class), eq(request));
        when(handler.update(eq(e.getId()), any(Permiso.class))).thenAnswer(invocation -> invocation.getArgument(1));
        when(mapper.toResponse(any(Permiso.class))).thenAnswer(invocation -> toResponse(invocation.getArgument(0)));

        PermisoResponse result = svc.update(e.getId(), request);

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
