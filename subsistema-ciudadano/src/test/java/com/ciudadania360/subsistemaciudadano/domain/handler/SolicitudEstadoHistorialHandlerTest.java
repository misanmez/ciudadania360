package com.ciudadania360.subsistemaciudadano.domain.handler;

import com.ciudadania360.subsistemaciudadano.domain.entity.Ciudadano;
import com.ciudadania360.subsistemaciudadano.domain.entity.Solicitud;
import com.ciudadania360.subsistemaciudadano.domain.entity.SolicitudEstadoHistorial;
import com.ciudadania360.subsistemaciudadano.domain.repository.SolicitudEstadoHistorialRepository;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SolicitudEstadoHistorialHandlerTest {

    @Mock
    private SolicitudEstadoHistorialRepository repo;

    @InjectMocks
    private SolicitudEstadoHistorialHandler handler;

    private Solicitud buildSolicitud() {
        Ciudadano ciudadano = Ciudadano.builder()
                .id(UUID.randomUUID())
                .nifNie("12345678A")
                .nombre("Juan")
                .apellidos("Pérez Gómez")
                .consentimientoLOPD(true)
                .estado("Activo")
                .build();

        return Solicitud.builder()
                .id(UUID.randomUUID())
                .ciudadano(ciudadano)
                .titulo("Solicitud de prueba")
                .tipo("queja")
                .estado("pendiente")
                .prioridad("MEDIA")
                .fechaRegistro(Instant.now())
                .scoreRelevancia(BigDecimal.valueOf(0.75))
                .encuestaEnviada(false)
                .build();
    }

    private SolicitudEstadoHistorial buildHistorial() {
        return SolicitudEstadoHistorial.builder()
                .id(UUID.randomUUID())
                .solicitud(buildSolicitud())
                .estadoAnterior("PENDIENTE")
                .estadoNuevo("COMPLETADA")
                .fechaCambio(Instant.now())
                .agente("usuario1")
                .metadata("{\"nota\":\"test\"}")
                .version(1L)
                .build();
    }

    @Test
    void listReturnsAllHistoriales() {
        SolicitudEstadoHistorial h = buildHistorial();
        when(repo.findAll()).thenReturn(List.of(h));

        List<SolicitudEstadoHistorial> result = handler.list();

        assertThat(result).containsExactly(h);
        assertThat(result.get(0).getSolicitud().getTitulo()).isEqualTo("Solicitud de prueba");
        verify(repo).findAll();
    }

    @Test
    void getReturnsHistorialById() {
        SolicitudEstadoHistorial h = buildHistorial();
        when(repo.findById(h.getId())).thenReturn(Optional.of(h));

        SolicitudEstadoHistorial result = handler.get(h.getId());

        assertThat(result).isEqualTo(h);
        assertThat(result.getSolicitud().getEstado()).isEqualTo("pendiente");
        verify(repo).findById(h.getId());
    }

    @Test
    void createSavesHistorial() {
        SolicitudEstadoHistorial h = buildHistorial();
        when(repo.save(any())).thenReturn(h);

        SolicitudEstadoHistorial result = handler.create(h);

        assertThat(result).isEqualTo(h);
        assertThat(result.getEstadoNuevo()).isEqualTo("COMPLETADA");
        assertThat(result.getSolicitud().getPrioridad()).isEqualTo("MEDIA");
        verify(repo).save(h);
    }

    @Test
    void updateSavesExistingHistorial() {
        SolicitudEstadoHistorial h = buildHistorial();
        when(repo.findById(h.getId())).thenReturn(Optional.of(h));
        when(repo.save(any())).thenReturn(h);

        SolicitudEstadoHistorial result = handler.update(h.getId(), h);

        assertThat(result).isEqualTo(h);
        assertThat(result.getVersion()).isEqualTo(1L);
        verify(repo).save(h);
    }

    @Test
    void deleteRemovesHistorialById() {
        UUID id = UUID.randomUUID();

        handler.delete(id);

        verify(repo).deleteById(id);
    }
}
