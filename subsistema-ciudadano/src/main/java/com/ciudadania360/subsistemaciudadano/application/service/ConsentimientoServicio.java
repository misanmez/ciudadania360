package com.ciudadania360.subsistemaciudadano.application.service;

import com.ciudadania360.subsistemaciudadano.domain.entity.Consentimiento;
import com.ciudadania360.subsistemaciudadano.domain.handler.ConsentimientoHandler;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class ConsentimientoServicio {
    private final ConsentimientoHandler handler;
    public ConsentimientoServicio(ConsentimientoHandler handler) { this.handler = handler; }
    public List<Consentimiento> list() { return handler.list(); }
    public Consentimiento get(UUID id) { return handler.get(id); }
    public Consentimiento create(Consentimiento e) { return handler.create(e); }
    public Consentimiento update(UUID id, Consentimiento e) { return handler.update(id, e); }
    public void delete(UUID id) { handler.delete(id); }
}
