package com.ciudadania360.shared.application.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SolicitudDTOTest {
    @Test
    void gettersAndSetters(){
        SolicitudDTO dto = new SolicitudDTO();
        dto.setTitulo("t");
        assertEquals("t", dto.getTitulo());
    }
}
