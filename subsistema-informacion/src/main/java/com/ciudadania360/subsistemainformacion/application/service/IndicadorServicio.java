package com.ciudadania360.subsistemainformacion.application.service;

import com.ciudadania360.subsistemainformacion.domain.entity.Indicador;
import com.ciudadania360.subsistemainformacion.domain.handler.IndicadorHandler;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class IndicadorServicio {
    private final IndicadorHandler handler;
    public IndicadorServicio(IndicadorHandler handler) { this.handler = handler; }
    public List<Indicador> list() { return handler.list(); }
    public Indicador get(UUID id) { return handler.get(id); }
    public Indicador create(Indicador e) { return handler.create(e); }
    public Indicador update(UUID id, Indicador e) { return handler.update(id, e); }
    public void delete(UUID id) { handler.delete(id); }
}
