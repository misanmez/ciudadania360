package com.ciudadania360.subsistemaciudadano.domain.handler;

import com.ciudadania360.subsistemaciudadano.domain.entity.Ciudadano;
import com.ciudadania360.subsistemaciudadano.domain.entity.Encuesta;
import com.ciudadania360.subsistemaciudadano.domain.entity.Solicitud;
import com.ciudadania360.subsistemaciudadano.domain.repository.EncuestaRepository;
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
class EncuestaHandlerTest {

    @Mock
    private EncuestaRepository repo;

    @InjectMocks
    private EncuestaHandler handler;

    private Solicitud buildSolicitud() {
        Ciudadano ciudadano = Ciudadano.builder()
                .id(UUID.randomUUID())
                .nifNie("12345678A")
                .nombre("Juan")
                .apellidos("Pérez Gómez")
                .email("juan@example.com")
                .telefono("600123456")
                .consentimientoLOPD(true)
                .estado("Activo")
                .build();

        return Solicitud.builder()
                .id(UUID.randomUUID())
                .ciudadano(ciudadano)
                .titulo("Problema con el servicio")
                .descripcion("Descripción detallada del problema")
                .tipo("queja")
                .canalEntrada("web")
                .estado("pendiente")
                .prioridad("ALTA")
                .numeroExpediente("EXP-001")
                .fechaRegistro(Instant.now())
                .fechaLimiteSLA(Instant.now().plusSeconds(86400))
                .scoreRelevancia(BigDecimal.valueOf(0.95))
                .origen("portal")
                .adjuntosCount(2)
                .encuestaEnviada(false)
                .referenciaExterna("REF-EXT-001")
                .metadata("{\"tag\":\"urgente\"}")
                .agenteAsignado("Agente 1")
                .version(1L)
                .build();
    }

    private Encuesta buildEncuesta() {
        return Encuesta.builder()
                .id(UUID.randomUUID())
                .solicitud(buildSolicitud())
                .tipo("Satisfacción")
                .estado("ENVIADA")
                .fechaEnvio(Instant.now())
                .fechaRespuesta(null)
                .respuestas("{\"q1\":\"sí\",\"q2\":\"no\"}")
                .metadata("{\"nota\":\"encuesta inicial\"}")
                .version(1L)
                .build();
    }

    @Test
    void listReturnsAllEncuestas() {
        Encuesta e = buildEncuesta();
        when(repo.findAll()).thenReturn(List.of(e));

        List<Encuesta> result = handler.list();

        assertThat(result).containsExactly(e);
        assertThat(result.get(0).getSolicitud().getTitulo()).isEqualTo("Problema con el servicio");
        verify(repo).findAll();
    }

    @Test
    void getReturnsEncuestaById() {
        Encuesta e = buildEncuesta();
        when(repo.findById(e.getId())).thenReturn(Optional.of(e));

        Encuesta result = handler.get(e.getId());

        assertThat(result).isEqualTo(e);
        assertThat(result.getTipo()).isEqualTo("Satisfacción");
        assertThat(result.getSolicitud().getEstado()).isEqualTo("pendiente");
        verify(repo).findById(e.getId());
    }

    @Test
    void createSavesEncuesta() {
        Encuesta e = buildEncuesta();
        when(repo.save(any())).thenReturn(e);

        Encuesta result = handler.create(e);

        assertThat(result).isEqualTo(e);
        assertThat(result.getEstado()).isEqualTo("ENVIADA");
        assertThat(result.getSolicitud().getPrioridad()).isEqualTo("ALTA");
        verify(repo).save(e);
    }

    @Test
    void updateSavesExistingEncuesta() {
        Encuesta e = buildEncuesta();
        when(repo.findById(e.getId())).thenReturn(Optional.of(e));
        when(repo.save(any())).thenReturn(e);

        Encuesta result = handler.update(e.getId(), e);

        assertThat(result).isEqualTo(e);
        assertThat(result.getVersion()).isEqualTo(1L);
        assertThat(result.getSolicitud().getNumeroExpediente()).isEqualTo("EXP-001");
        verify(repo).save(e);
    }

    @Test
    void deleteRemovesEncuestaById() {
        UUID id = UUID.randomUUID();

        handler.delete(id);

        verify(repo).deleteById(id);
    }
}
