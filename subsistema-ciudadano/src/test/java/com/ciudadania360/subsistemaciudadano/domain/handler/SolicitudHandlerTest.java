package com.ciudadania360.subsistemaciudadano.domain.handler;

import com.ciudadania360.subsistemaciudadano.domain.entity.Ciudadano;
import com.ciudadania360.subsistemaciudadano.domain.entity.Clasificacion;
import com.ciudadania360.subsistemaciudadano.domain.entity.Solicitud;
import com.ciudadania360.subsistemaciudadano.domain.entity.Ubicacion;
import com.ciudadania360.subsistemaciudadano.domain.repository.SolicitudRepositorio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SolicitudHandlerTest {

    @Mock
    private SolicitudRepositorio repo;

    @InjectMocks
    private SolicitudHandler handler;

    private Ciudadano createCiudadano() {
        return Ciudadano.builder()
                .id(UUID.randomUUID())
                .nombre("Juan")
                .apellidos("Pérez")
                .email("juan@example.com")
                .telefono("123456789")
                .consentimientoLOPD(true)
                .build();
    }

    private Clasificacion createClasificacion() {
        return Clasificacion.builder()
                .id(UUID.randomUUID())
                .codigo("C001")
                .nombre("Clasificación X")
                .build();
    }

    private Ubicacion createUbicacion() {
        return Ubicacion.builder()
                .id(UUID.randomUUID())
                .direccion("Calle Falsa 123")
                .municipio("Madrid")
                .provincia("Madrid")
                .cp("28001")
                .lat(40.4168)
                .lon(-3.7038)
                .precision(5)
                .fuente("Sistema Test")
                .build();
    }

    private Solicitud createSolicitud(UUID id) {
        return Solicitud.builder()
                .id(id)
                .ciudadano(createCiudadano())
                .clasificacion(createClasificacion())
                .ubicacion(createUbicacion())
                .titulo("Solicitud Test")
                .descripcion("Descripción test")
                .tipo("Tipo A")
                .canalEntrada("Email")
                .estado("Abierta")
                .prioridad("Alta")
                .numeroExpediente("EXP-001")
                .fechaRegistro(Instant.now())
                .fechaLimiteSLA(Instant.now().plusSeconds(3600))
                .fechaCierre(null)
                .scoreRelevancia(new BigDecimal("0.75"))
                .origen("Portal")
                .adjuntosCount(2)
                .encuestaEnviada(false)
                .referenciaExterna("REF-123")
                .metadata("{\"key\":\"value\"}")
                .build();
    }

    @Test
    void listReturnsAllSolicitudes() {
        Solicitud e = createSolicitud(UUID.randomUUID());

        when(repo.findAll()).thenReturn(List.of(e));

        List<Solicitud> result = handler.obtenerTodos();

        assertThat(result).containsExactly(e);
        verify(repo).findAll();
    }

    @Test
    void getReturnsSolicitudById() {
        UUID id = UUID.randomUUID();
        Solicitud e = createSolicitud(id);

        when(repo.findById(id)).thenReturn(Optional.of(e));

        Solicitud result = handler.obtenerPorId(id);

        assertThat(result).isEqualTo(e);
        verify(repo).findById(id);
    }

    @Test
    void createSavesSolicitud() {
        Solicitud e = createSolicitud(UUID.randomUUID());

        when(repo.save(any())).thenReturn(e);

        Solicitud result = handler.crear(e);

        assertThat(result).isEqualTo(e);
        verify(repo).save(e);
    }

    @Test
    void updateSavesExistingSolicitud() {
        UUID id = UUID.randomUUID();
        Solicitud existente = createSolicitud(id);
        Solicitud cambios = createSolicitud(id);
        cambios.setTitulo("Nuevo título");

        when(repo.findById(id)).thenReturn(Optional.of(existente));
        when(repo.save(any())).thenReturn(existente);

        Solicitud result = handler.actualizar(id, cambios);

        assertThat(result.getTitulo()).isEqualTo("Nuevo título");
        verify(repo).save(existente);
    }


    @Test
    void deleteRemovesSolicitudById() {
        UUID id = UUID.randomUUID();

        handler.eliminar(id);

        verify(repo).deleteById(id);
    }
}