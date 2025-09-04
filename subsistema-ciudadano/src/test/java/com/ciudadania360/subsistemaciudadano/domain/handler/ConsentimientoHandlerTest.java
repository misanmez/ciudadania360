package com.ciudadania360.subsistemaciudadano.domain.handler;

import com.ciudadania360.subsistemaciudadano.domain.entity.Ciudadano;
import com.ciudadania360.subsistemaciudadano.domain.entity.Consentimiento;
import com.ciudadania360.subsistemaciudadano.domain.repository.ConsentimientoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsentimientoHandlerTest {

    @Mock
    private ConsentimientoRepository repo;

    @InjectMocks
    private ConsentimientoHandler handler;

    private Consentimiento buildConsentimiento() {
        Ciudadano ciudadano = Ciudadano.builder()
                .id(UUID.randomUUID())
                .nifNie("12345678A")
                .nombre("Juan")
                .apellidos("Pérez Gómez")
                .email("juan@example.com")
                .telefono("600123456")
                .canalPreferido("Email")
                .direccionPostal("Calle Falsa 123")
                .consentimientoLOPD(true)
                .estado("Activo")
                .externalId("EXT123")
                .metadata("{\"origen\":\"test\"}")
                .build();

        return Consentimiento.builder()
                .id(UUID.randomUUID())
                .ciudadano(ciudadano)
                .tipo("LOPD")
                .otorgado(true)
                .fechaOtorgamiento(new Date())
                .fechaRevocacion(null)
                .metadata("{\"nota\":\"consentimiento inicial\"}")
                .version(1L)
                .build();
    }

    @Test
    void listReturnsAllConsentimientos() {
        Consentimiento c = buildConsentimiento();
        when(repo.findAll()).thenReturn(List.of(c));

        List<Consentimiento> result = handler.list();

        assertThat(result).containsExactly(c);
        assertThat(result.get(0).getCiudadano().getNombre()).isEqualTo("Juan");
        verify(repo).findAll();
    }

    @Test
    void getReturnsConsentimientoById() {
        Consentimiento c = buildConsentimiento();
        when(repo.findById(c.getId())).thenReturn(Optional.of(c));

        Consentimiento result = handler.get(c.getId());

        assertThat(result).isEqualTo(c);
        assertThat(result.getTipo()).isEqualTo("LOPD");
        assertThat(result.getCiudadano().getNifNie()).isEqualTo("12345678A");
        verify(repo).findById(c.getId());
    }

    @Test
    void createSavesConsentimiento() {
        Consentimiento c = buildConsentimiento();
        when(repo.save(any())).thenReturn(c);

        Consentimiento result = handler.create(c);

        assertThat(result).isEqualTo(c);
        assertThat(result.getOtorgado()).isTrue();
        assertThat(result.getMetadata()).contains("consentimiento inicial");
        verify(repo).save(c);
    }

    @Test
    void updateSavesExistingConsentimiento() {
        Consentimiento c = buildConsentimiento();
        when(repo.findById(c.getId())).thenReturn(Optional.of(c));
        when(repo.save(any())).thenReturn(c);

        Consentimiento result = handler.update(c.getId(), c);

        assertThat(result).isEqualTo(c);
        assertThat(result.getVersion()).isEqualTo(1L);
        verify(repo).save(c);
    }

    @Test
    void deleteRemovesConsentimientoById() {
        UUID id = UUID.randomUUID();

        handler.delete(id);

        verify(repo).deleteById(id);
    }
}
