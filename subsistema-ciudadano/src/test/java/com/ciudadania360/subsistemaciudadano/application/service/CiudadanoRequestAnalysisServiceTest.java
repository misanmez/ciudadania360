package com.ciudadania360.subsistemaciudadano.application.service;

import com.ciudadania360.shared.application.service.IAService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class CiudadanoRequestAnalysisServiceTest {

    @Mock
    private IAService iaService;

    @InjectMocks
    private CiudadanoRequestAnalysisService service;

    @Test
    void classifyRequestDelegatesToIAService() {
        String content = "Solicitud de empadronamiento";
        String classification = "Empadronamiento";
        when(iaService.classifyDocument(content, "request-type")).thenReturn(classification);

        String result = service.classifyRequest(content);

        assertThat(result).isEqualTo(classification);
        verify(iaService).classifyDocument(content, "request-type");
    }
}
