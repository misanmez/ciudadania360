package com.ciudadania360.subsistemainformacion.domain.handler;

import com.ciudadania360.subsistemainformacion.domain.entity.Dataset;
import com.ciudadania360.subsistemainformacion.domain.repository.DatasetRepositorio;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class DatasetHandler {
    private final DatasetRepositorio repo;
    public DatasetHandler(DatasetRepositorio repo) { this.repo = repo; }

    public List<Dataset> list() { return repo.findAll(); }
    public Dataset get(UUID id) { return repo.findById(id).orElseThrow(() -> new NoSuchElementException("Dataset no encontrado")); }
    public Dataset create(Dataset e) { if(e.getId()==null) e.setId(UUID.randomUUID()); return repo.save(e); }
    public Dataset update(UUID id, Dataset e) { Dataset cur = get(id); e.setId(cur.getId()); return repo.save(e); }
    public void delete(UUID id) { repo.deleteById(id); }
}
