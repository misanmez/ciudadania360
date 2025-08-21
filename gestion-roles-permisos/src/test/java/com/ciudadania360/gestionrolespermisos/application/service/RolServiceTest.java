package com.ciudadania360.gestionrolespermisos.application.service;

import com.ciudadania360.gestionrolespermisos.application.dto.rol.RolRequest;
import com.ciudadania360.gestionrolespermisos.application.dto.rol.RolResponse;
import com.ciudadania360.gestionrolespermisos.application.mapper.RolMapper;
import com.ciudadania360.gestionrolespermisos.domain.entity.Rol;
import com.ciudadania360.gestionrolespermisos.domain.handler.RolHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RolServiceTest {

    @Mock
    private RolHandler handler;

    @Mock
    private RolMapper mapper;

    @InjectMocks
    private RolService svc;

    private static final UUID FIXED_ID = UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");

    private Rol buildRol() {
        return Rol.builder()
                .id(FIXED_ID)
                .codigo("ROL001")
                .nombre("Rol Test")
                .descripcion("Descripción de prueba")
                .activo(true)
                .build();
    }

    private RolRequest toRequest(Rol e) {
        return new RolRequest(e.getCodigo(), e.getNombre(), e.getDescripcion(), e.getNivel(), e.getActivo());
    }

    private RolResponse toResponse(Rol e) {
        return new RolResponse(e.getId(), e.getCodigo(), e.getNombre(), e.getDescripcion(), e.getNivel(), e.getActivo());
    }

    @Test
    void listDelegatesToHandler() {
        Rol e = buildRol();
        when(handler.list()).thenReturn(List.of(e));
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        List<RolResponse> result = svc.list();

        assertThat(result).containsExactly(toResponse(e));
        verify(handler).list();
        verify(mapper).toResponse(e);
    }

    @Test
    void getDelegatesToHandler() {
        Rol e = buildRol();
        when(handler.get(e.getId())).thenReturn(e);
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        RolResponse result = svc.get(e.getId());

        assertThat(result).isEqualTo(toResponse(e));
        verify(handler).get(e.getId());
        verify(mapper).toResponse(e);
    }

    @Test
    void createDelegatesToHandler() {
        Rol e = buildRol();
        RolRequest request = toRequest(e);

        when(mapper.toEntity(request)).thenReturn(e);
        when(handler.create(any(Rol.class))).thenAnswer(invocation -> {
            Rol arg = invocation.getArgument(0);
            arg.setId(FIXED_ID);
            return arg;
        });
        when(mapper.toResponse(any(Rol.class))).thenAnswer(invocation -> toResponse(invocation.getArgument(0)));

        RolResponse result = svc.create(request);

        assertThat(result).isEqualTo(toResponse(e));
    }

    @Test
    void updateDelegatesToHandler() {
        Rol e = buildRol();
        RolRequest request = toRequest(e);

        when(handler.get(e.getId())).thenReturn(e);
        doNothing().when(mapper).updateEntity(any(Rol.class), eq(request));
        when(handler.update(eq(e.getId()), any(Rol.class))).thenAnswer(invocation -> invocation.getArgument(1));
        when(mapper.toResponse(any(Rol.class))).thenAnswer(invocation -> toResponse(invocation.getArgument(0)));

        RolResponse result = svc.update(e.getId(), request);

        assertThat(result).isEqualTo(toResponse(e));
    }

    @Test
    void deleteDelegatesToHandler() {
        UUID id = FIXED_ID;

        svc.delete(id);

        verify(handler).delete(id);
        verifyNoInteractions(mapper);
    }
}
