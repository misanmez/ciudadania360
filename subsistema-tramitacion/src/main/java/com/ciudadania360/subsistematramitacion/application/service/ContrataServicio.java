package com.ciudadania360.subsistematramitacion.application.service;

import com.ciudadania360.subsistematramitacion.domain.entity.Contrata;
import com.ciudadania360.subsistematramitacion.domain.handler.ContrataHandler;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class ContrataServicio {
    private final ContrataHandler handler;
    public ContrataServicio(ContrataHandler handler) { this.handler = handler; }
    public List<Contrata> list() { return handler.list(); }
    public Contrata get(UUID id) { return handler.get(id); }
    public Contrata create(Contrata e) { return handler.create(e); }
    public Contrata update(UUID id, Contrata e) { return handler.update(id, e); }
    public void delete(UUID id) { handler.delete(id); }
}
