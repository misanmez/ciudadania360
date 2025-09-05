package com.ciudadania360.subsistemaciudadano.application.service;

import com.ciudadania360.subsistemaciudadano.application.dto.ciudadano.CiudadanoResponse;
import com.ciudadania360.subsistemaciudadano.application.dto.interaccion.InteraccionRequest;
import com.ciudadania360.subsistemaciudadano.application.dto.interaccion.InteraccionResponse;
import com.ciudadania360.subsistemaciudadano.application.dto.solicitud.SolicitudResponse;
import com.ciudadania360.subsistemaciudadano.application.mapper.InteraccionMapper;
import com.ciudadania360.subsistemaciudadano.application.validator.InteraccionValidator;
import com.ciudadania360.subsistemaciudadano.domain.entity.Ciudadano;
import com.ciudadania360.subsistemaciudadano.domain.entity.Interaccion;
import com.ciudadania360.subsistemaciudadano.domain.entity.Solicitud;
import com.ciudadania360.subsistemaciudadano.domain.handler.InteraccionHandler;
import com.ciudadania360.subsistemainterno.application.dto.empleado.EmpleadoResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class InteraccionServiceTest {

    @Mock
    private InteraccionHandler handler;

    @Mock
    private InteraccionMapper mapper;

    @Mock
    private InteraccionValidator validator;

    @InjectMocks
    private InteraccionService service;

    @BeforeEach
    void initMocks() {
        MockitoAnnotations.openMocks(this);
    }

    private Interaccion buildEntity() {
        UUID ciudadanoId = UUID.fromString("00000000-0000-0000-0000-000000000001");
        UUID solicitudId = UUID.fromString("00000000-0000-0000-0000-000000000002");
        UUID interaccionId = UUID.fromString("00000000-0000-0000-0000-000000000003");

        Ciudadano ciudadano = Ciudadano.builder().id(ciudadanoId).build();
        Solicitud solicitud = Solicitud.builder().id(solicitudId).build();

        return Interaccion.builder()
                .id(interaccionId)
                .ciudadano(ciudadano)
                .solicitud(solicitud)
                .canal("email")
                .fecha(Instant.parse("2025-01-01T10:00:00Z"))
                .agente("Agente1")
                .mensaje("Mensaje de prueba")
                .adjuntoUri(null)
                .visibilidad("PUBLICO")
                .version(1L)
                .build();
    }

    private InteraccionRequest buildRequest(UUID ciudadanoId, UUID solicitudId) {
        return InteraccionRequest.builder()
                .ciudadanoId(ciudadanoId)
                .solicitudId(solicitudId)
                .canal("email")
                .fecha(Instant.parse("2025-01-01T10:00:00Z"))
                .agente("Agente1")
                .mensaje("Mensaje de prueba")
                .adjuntoUri(null)
                .visibilidad("PUBLICO")
                .build();
    }

    private InteraccionResponse toResponse(Interaccion entity) {
        return InteraccionResponse.builder()
                .id(entity.getId())
                .ciudadano(CiudadanoResponse.builder()
                        .id(entity.getCiudadano().getId())
                        .nombre(entity.getCiudadano().getNombre())
                        .apellidos(entity.getCiudadano().getApellidos())
                        .email(entity.getCiudadano().getEmail())
                        .telefono(entity.getCiudadano().getTelefono())
                        .build())
                .solicitud(SolicitudResponse.builder()
                        .id(entity.getSolicitud().getId())
                        .titulo(entity.getSolicitud().getTitulo())
                        .estado(entity.getSolicitud().getEstado())
                        .prioridad(entity.getSolicitud().getPrioridad())
                        .numeroExpediente(entity.getSolicitud().getNumeroExpediente())
                        .build())
                .empleadoResponsable(EmpleadoResponse.builder()
                        .id(entity.getEmpleadoResponsable() != null ? entity.getEmpleadoResponsable().getId() : null)
                        .nombre(entity.getEmpleadoResponsable() != null ? entity.getEmpleadoResponsable().getNombre() : null)
                        .apellidos(entity.getEmpleadoResponsable() != null ? entity.getEmpleadoResponsable().getApellidos() : null)
                        .email(entity.getEmpleadoResponsable() != null ? entity.getEmpleadoResponsable().getEmail() : null)
                        .telefono(entity.getEmpleadoResponsable() != null ? entity.getEmpleadoResponsable().getTelefono() : null)
                        .rol(entity.getEmpleadoResponsable() != null ? entity.getEmpleadoResponsable().getRol() : null)
                        .build())
                .canal(entity.getCanal())
                .fecha(entity.getFecha())
                .agente(entity.getAgente())
                .mensaje(entity.getMensaje())
                .adjuntoUri(entity.getAdjuntoUri())
                .visibilidad(entity.getVisibilidad())
                .version(entity.getVersion())
                .build();
    }


    @Test
    void listDelegatesToHandler() {
        Interaccion entity = buildEntity();
        when(handler.list()).thenReturn(List.of(entity));
        when(mapper.toResponse(entity)).thenReturn(toResponse(entity));

        List<InteraccionResponse> result = service.list();

        assertThat(result).containsExactly(toResponse(entity));
        verify(handler).list();
        verify(mapper).toResponse(entity);
    }

    @Test
    void getDelegatesToHandler() {
        Interaccion entity = buildEntity();
        when(handler.get(entity.getId())).thenReturn(entity);
        when(mapper.toResponse(entity)).thenReturn(toResponse(entity));

        InteraccionResponse result = service.get(entity.getId());

        assertThat(result).isEqualTo(toResponse(entity));
        verify(handler).get(entity.getId());
        verify(mapper).toResponse(entity);
    }

    @Test
    void createDelegatesToHandler() {
        Interaccion entity = buildEntity();
        InteraccionRequest request = buildRequest(entity.getCiudadano().getId(), entity.getSolicitud().getId());
        InteraccionResponse expectedResponse = toResponse(entity);

        when(mapper.toEntity(request)).thenReturn(entity);
        when(handler.create(entity)).thenReturn(entity);
        when(mapper.toResponse(entity)).thenReturn(expectedResponse);

        InteraccionResponse result = service.create(request);

        assertThat(result).isEqualTo(expectedResponse);
        verify(validator).validateForCreate(request);
        verify(mapper).toEntity(request);
        verify(handler).create(entity);
        verify(mapper).toResponse(entity);
    }

    @Test
    void updateDelegatesToHandler() {
        Interaccion entity = buildEntity();
        InteraccionRequest request = buildRequest(entity.getCiudadano().getId(), entity.getSolicitud().getId());
        InteraccionResponse expectedResponse = toResponse(entity);

        when(handler.get(entity.getId())).thenReturn(entity);
        doNothing().when(mapper).updateEntity(entity, request);
        when(handler.update(entity.getId(), entity)).thenReturn(entity);
        when(mapper.toResponse(entity)).thenReturn(expectedResponse);

        InteraccionResponse result = service.update(entity.getId(), request);

        assertThat(result).isEqualTo(expectedResponse);
        verify(validator).validateForUpdate(request, entity);
        verify(handler).get(entity.getId());
        verify(mapper).updateEntity(entity, request);
        verify(handler).update(entity.getId(), entity);
        verify(mapper).toResponse(entity);
    }

    @Test
    void deleteDelegatesToHandler() {
        UUID id = UUID.fromString("00000000-0000-0000-0000-000000000004");
        Interaccion existing = buildEntity();

        when(handler.get(id)).thenReturn(existing);

        service.delete(id);

        // Verificaciones
        verify(handler).get(id);
        verify(validator).validateForDelete(existing);
        verify(handler).delete(id);
        verifyNoInteractions(mapper);
    }

}
