package com.ciudadania360.subsistemaciudadano.application.service;

import com.ciudadania360.shared.application.service.IAService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class CiudadanoRecommendationServiceTest {

    @Mock
    private IAService iaService;

    @InjectMocks
    private CiudadanoRecommendationService service;

    @Test
    void suggestTramitesDelegatesToIAService() {
        String history = "historial usuario";
        String prediction = "tramite1,tramite2";
        when(iaService.predict(history, "tramite-recommendation-model")).thenReturn(prediction);

        List<String> result = service.suggestTramites(history);

        assertThat(result).containsExactly("tramite1", "tramite2");
        verify(iaService).predict(history, "tramite-recommendation-model");
    }
}
