package com.ciudadania360.subsistemainformacion.application.service;

import com.ciudadania360.subsistemainformacion.domain.entity.Sugerencia;
import com.ciudadania360.subsistemainformacion.domain.handler.SugerenciaHandler;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class SugerenciaServicio {
    private final SugerenciaHandler handler;
    public SugerenciaServicio(SugerenciaHandler handler) { this.handler = handler; }
    public List<Sugerencia> list() { return handler.list(); }
    public Sugerencia get(UUID id) { return handler.get(id); }
    public Sugerencia create(Sugerencia e) { return handler.create(e); }
    public Sugerencia update(UUID id, Sugerencia e) { return handler.update(id, e); }
    public void delete(UUID id) { handler.delete(id); }
}
