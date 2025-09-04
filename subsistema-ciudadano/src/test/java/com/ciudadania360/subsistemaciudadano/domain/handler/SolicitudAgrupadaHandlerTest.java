package com.ciudadania360.subsistemaciudadano.domain.handler;

import com.ciudadania360.subsistemaciudadano.domain.entity.Ciudadano;
import com.ciudadania360.subsistemaciudadano.domain.entity.Solicitud;
import com.ciudadania360.subsistemaciudadano.domain.entity.SolicitudAgrupada;
import com.ciudadania360.subsistemaciudadano.domain.repository.SolicitudAgrupadaRepository;
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
class SolicitudAgrupadaHandlerTest {

    @Mock
    private SolicitudAgrupadaRepository repo;

    @InjectMocks
    private SolicitudAgrupadaHandler handler;

    private Ciudadano buildCiudadano() {
        return Ciudadano.builder()
                .id(UUID.randomUUID())
                .nifNie("87654321B")
                .nombre("María")
                .apellidos("López")
                .consentimientoLOPD(true)
                .estado("Activo")
                .build();
    }

    private Solicitud buildSolicitud(String titulo) {
        return Solicitud.builder()
                .id(UUID.randomUUID())
                .ciudadano(buildCiudadano())
                .titulo(titulo)
                .tipo("incidencia")
                .estado("pendiente")
                .prioridad("ALTA")
                .fechaRegistro(Instant.now())
                .scoreRelevancia(BigDecimal.valueOf(0.8))
                .encuestaEnviada(false)
                .build();
    }

    private SolicitudAgrupada buildSolicitudAgrupada() {
        return SolicitudAgrupada.builder()
                .id(UUID.randomUUID())
                .solicitudPadre(buildSolicitud("Solicitud Padre"))
                .solicitudHija(buildSolicitud("Solicitud Hija"))
                .metadata("{\"agrupada\":\"test\"}")
                .version(1L)
                .build();
    }

    @Test
    void listReturnsAllAgrupadas() {
        SolicitudAgrupada sa = buildSolicitudAgrupada();
        when(repo.findAll()).thenReturn(List.of(sa));

        List<SolicitudAgrupada> result = handler.list();

        assertThat(result).containsExactly(sa);
        assertThat(result.get(0).getSolicitudPadre().getTitulo()).isEqualTo("Solicitud Padre");
        assertThat(result.get(0).getSolicitudHija().getTitulo()).isEqualTo("Solicitud Hija");
        verify(repo).findAll();
    }

    @Test
    void getReturnsSolicitudAgrupadaById() {
        SolicitudAgrupada sa = buildSolicitudAgrupada();
        when(repo.findById(sa.getId())).thenReturn(Optional.of(sa));

        SolicitudAgrupada result = handler.get(sa.getId());

        assertThat(result).isEqualTo(sa);
        assertThat(result.getSolicitudPadre().getPrioridad()).isEqualTo("ALTA");
        verify(repo).findById(sa.getId());
    }

    @Test
    void createSavesSolicitudAgrupada() {
        SolicitudAgrupada sa = buildSolicitudAgrupada();
        when(repo.save(any())).thenReturn(sa);

        SolicitudAgrupada result = handler.create(sa);

        assertThat(result).isEqualTo(sa);
        assertThat(result.getMetadata()).contains("agrupada");
        verify(repo).save(sa);
    }

    @Test
    void updateSavesExistingSolicitudAgrupada() {
        SolicitudAgrupada sa = buildSolicitudAgrupada();
        when(repo.findById(sa.getId())).thenReturn(Optional.of(sa));
        when(repo.save(any())).thenReturn(sa);

        SolicitudAgrupada result = handler.update(sa.getId(), sa);

        assertThat(result).isEqualTo(sa);
        assertThat(result.getVersion()).isEqualTo(1L);
        verify(repo).save(sa);
    }

    @Test
    void deleteRemovesSolicitudAgrupadaById() {
        UUID id = UUID.randomUUID();

        handler.delete(id);

        verify(repo).deleteById(id);
    }
}
