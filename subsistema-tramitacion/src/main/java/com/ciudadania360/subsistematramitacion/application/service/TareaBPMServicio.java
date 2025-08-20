package com.ciudadania360.subsistematramitacion.application.service;

import com.ciudadania360.subsistematramitacion.domain.entity.TareaBPM;
import com.ciudadania360.subsistematramitacion.domain.handler.TareaBPMHandler;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class TareaBPMServicio {
    private final TareaBPMHandler handler;
    public TareaBPMServicio(TareaBPMHandler handler) { this.handler = handler; }
    public List<TareaBPM> list() { return handler.list(); }
    public TareaBPM get(UUID id) { return handler.get(id); }
    public TareaBPM create(TareaBPM e) { return handler.create(e); }
    public TareaBPM update(UUID id, TareaBPM e) { return handler.update(id, e); }
    public void delete(UUID id) { handler.delete(id); }
}
