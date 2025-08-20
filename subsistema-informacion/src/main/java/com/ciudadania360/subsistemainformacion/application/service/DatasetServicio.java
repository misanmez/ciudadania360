package com.ciudadania360.subsistemainformacion.application.service;

import com.ciudadania360.subsistemainformacion.domain.entity.Dataset;
import com.ciudadania360.subsistemainformacion.domain.handler.DatasetHandler;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class DatasetServicio {
    private final DatasetHandler handler;
    public DatasetServicio(DatasetHandler handler) { this.handler = handler; }
    public List<Dataset> list() { return handler.list(); }
    public Dataset get(UUID id) { return handler.get(id); }
    public Dataset create(Dataset e) { return handler.create(e); }
    public Dataset update(UUID id, Dataset e) { return handler.update(id, e); }
    public void delete(UUID id) { handler.delete(id); }
}
