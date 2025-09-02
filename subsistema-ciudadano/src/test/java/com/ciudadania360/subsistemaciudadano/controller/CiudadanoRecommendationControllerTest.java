package com.ciudadania360.subsistemaciudadano.controller;

import com.ciudadania360.subsistemaciudadano.application.service.CiudadanoRecommendationService;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CiudadanoRecommendationControllerTest {

    @Test
    void suggestTramites() throws Exception {
        CiudadanoRecommendationService service = mock(CiudadanoRecommendationService.class);
        when(service.suggestTramites("historial usuario")).thenReturn(List.of("tramite1", "tramite2"));

        CiudadanoRecommendationController controller = new CiudadanoRecommendationController(service);
        MockMvc mvc = MockMvcBuilders.standaloneSetup(controller).build();

        mvc.perform(get("/api/ciudadano/recommendations")
                        .param("userHistory", "historial usuario")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
