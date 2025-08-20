package com.ciudadania360.subsistemainformacion.application.service;

import com.ciudadania360.subsistemainformacion.domain.entity.Instruccion;
import com.ciudadania360.subsistemainformacion.domain.handler.InstruccionHandler;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class InstruccionServicio {
    private final InstruccionHandler handler;
    public InstruccionServicio(InstruccionHandler handler) { this.handler = handler; }
    public List<Instruccion> list() { return handler.list(); }
    public Instruccion get(UUID id) { return handler.get(id); }
    public Instruccion create(Instruccion e) { return handler.create(e); }
    public Instruccion update(UUID id, Instruccion e) { return handler.update(id, e); }
    public void delete(UUID id) { handler.delete(id); }
}
