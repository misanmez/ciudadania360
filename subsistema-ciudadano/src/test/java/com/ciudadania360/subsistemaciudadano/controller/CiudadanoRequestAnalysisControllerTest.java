package com.ciudadania360.subsistemaciudadano.controller;

import com.ciudadania360.subsistemaciudadano.application.service.CiudadanoRequestAnalysisService;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CiudadanoRequestAnalysisControllerTest {

    @Test
    void classifyRequest() throws Exception {
        CiudadanoRequestAnalysisService service = mock(CiudadanoRequestAnalysisService.class);
        when(service.classifyRequest("Solicitud de empadronamiento")).thenReturn("Empadronamiento");

        CiudadanoRequestAnalysisController controller = new CiudadanoRequestAnalysisController(service);
        MockMvc mvc = MockMvcBuilders.standaloneSetup(controller).build();

        mvc.perform(post("/api/ciudadano/requests/classify")
                        .param("content", "Solicitud de empadronamiento"))
                .andExpect(status().isOk());
    }

}
