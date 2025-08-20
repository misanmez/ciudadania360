package com.ciudadania360.subsistematramitacion.application.service;

import com.ciudadania360.subsistematramitacion.domain.entity.ProcesoBPM;
import com.ciudadania360.subsistematramitacion.domain.handler.ProcesoBPMHandler;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class ProcesoBPMServicio {
    private final ProcesoBPMHandler handler;
    public ProcesoBPMServicio(ProcesoBPMHandler handler) { this.handler = handler; }
    public List<ProcesoBPM> list() { return handler.list(); }
    public ProcesoBPM get(UUID id) { return handler.get(id); }
    public ProcesoBPM create(ProcesoBPM e) { return handler.create(e); }
    public ProcesoBPM update(UUID id, ProcesoBPM e) { return handler.update(id, e); }
    public void delete(UUID id) { handler.delete(id); }
}
