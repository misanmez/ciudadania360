package com.ciudadania360.subsistemaciudadano.application.service;

import com.ciudadania360.subsistemaciudadano.application.dto.ciudadano.CiudadanoRequest;
import com.ciudadania360.subsistemaciudadano.application.dto.ciudadano.CiudadanoResponse;
import com.ciudadania360.subsistemaciudadano.application.mapper.CiudadanoMapper;
import com.ciudadania360.subsistemaciudadano.domain.entity.Ciudadano;
import com.ciudadania360.subsistemaciudadano.domain.handler.CiudadanoHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class CiudadanoServiceTest {

    @Mock
    private CiudadanoHandler handler;

    @Mock
    private CiudadanoMapper mapper; // 🔑 Mock del mapper

    @InjectMocks
    private CiudadanoService svc;

    private Ciudadano buildCiudadano() {
        return Ciudadano.builder()
                .id(UUID.randomUUID())
                .nifNie("12345678A")
                .nombre("Juan")
                .apellidos("Pérez Gómez")
                .email("juan@example.com")
                .telefono("600123456")
                .canalPreferido("Email")
                .direccionPostal("Calle Falsa 123")
                .ubicacionId(UUID.randomUUID())
                .consentimientoLOPD(true)
                .estado("ACTIVO")
                .externalId("EXT123")
                .metadata("{\"key\":\"value\"}")
                .solicitudes(List.of())
                .version(1L)
                .build();
    }

    private CiudadanoRequest toRequest(Ciudadano e) {
        return new CiudadanoRequest(
                e.getNifNie(), e.getNombre(), e.getApellidos(),
                e.getEmail(), e.getTelefono(), e.getCanalPreferido(),
                e.getDireccionPostal(), e.getUbicacionId(),
                e.getConsentimientoLOPD(), e.getEstado(),
                e.getExternalId(), e.getMetadata()
        );
    }

    private CiudadanoResponse toResponse(Ciudadano e) {
        return new CiudadanoResponse(
                e.getId(), e.getNifNie(), e.getNombre(), e.getApellidos(),
                e.getEmail(), e.getTelefono(), e.getCanalPreferido(),
                e.getDireccionPostal(), e.getUbicacionId(),
                e.getConsentimientoLOPD(), e.getEstado(),
                e.getExternalId(), e.getMetadata()
        );
    }

    @Test
    void listDelegatesToHandler() {
        Ciudadano e = buildCiudadano();
        when(handler.list()).thenReturn(List.of(e));
        when(mapper.toResponse(e)).thenReturn(toResponse(e)); // 🔑 stub

        List<CiudadanoResponse> result = svc.list();

        assertThat(result).containsExactly(toResponse(e));
        verify(handler).list();
        verify(mapper).toResponse(e);
    }

    @Test
    void getDelegatesToHandler() {
        Ciudadano e = buildCiudadano();
        when(handler.get(e.getId())).thenReturn(e);
        when(mapper.toResponse(e)).thenReturn(toResponse(e)); // 🔑 stub

        CiudadanoResponse result = svc.get(e.getId());

        assertThat(result).isEqualTo(toResponse(e));
        verify(handler).get(e.getId());
        verify(mapper).toResponse(e);
    }

    @Test
    void createDelegatesToHandler() {
        Ciudadano e = buildCiudadano();
        CiudadanoRequest request = toRequest(e);
        CiudadanoResponse expectedResponse = toResponse(e);

        when(mapper.toEntity(request)).thenReturn(e);
        when(handler.create(e)).thenReturn(e);
        when(mapper.toResponse(e)).thenReturn(expectedResponse);

        CiudadanoResponse result = svc.create(request);

        assertThat(result).isEqualTo(expectedResponse);
        verify(mapper).toEntity(request);
        verify(handler).create(e);
        verify(mapper).toResponse(e);
    }


    @Test
    void updateDelegatesToHandler() {
        Ciudadano e = buildCiudadano();
        CiudadanoRequest request = toRequest(e);

        when(handler.get(e.getId())).thenReturn(e);
        // mapper.updateEntity(...) es void; por defecto en un mock no hace nada. Puedes verificar la invocación:
        doNothing().when(mapper).updateEntity(any(Ciudadano.class), eq(request));
        when(handler.update(eq(e.getId()), any(Ciudadano.class))).thenReturn(e);
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        CiudadanoResponse result = svc.update(e.getId(), request);

        assertThat(result).isEqualTo(toResponse(e));
        verify(handler).get(e.getId());
        verify(mapper).updateEntity(same(e), eq(request));
        verify(handler).update(eq(e.getId()), same(e));
        verify(mapper).toResponse(e);
    }

    @Test
    void deleteDelegatesToHandler() {
        UUID id = UUID.randomUUID();

        svc.delete(id);

        verify(handler).delete(id);
        verifyNoInteractions(mapper); // opcional
    }
}
