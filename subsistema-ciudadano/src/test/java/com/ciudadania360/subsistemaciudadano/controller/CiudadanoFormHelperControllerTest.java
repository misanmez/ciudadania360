package com.ciudadania360.subsistemaciudadano.controller;

import com.ciudadania360.subsistemaciudadano.application.service.CiudadanoFormHelperService;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CiudadanoFormHelperControllerTest {

    @Test
    void autocompleteForm() throws Exception {
        CiudadanoFormHelperService service = mock(CiudadanoFormHelperService.class);
        when(service.autocompleteForm("nombre: Jua")).thenReturn("nombre: Juan Pérez");

        CiudadanoFormHelperController controller = new CiudadanoFormHelperController(service);
        MockMvc mvc = MockMvcBuilders.standaloneSetup(controller).build();

        mvc.perform(post("/api/ciudadano/form/autocomplete")
                        .param("partialForm", "nombre: Jua"))
                .andExpect(status().isOk());
    }

}
