package com.ciudadania360.subsistemainterno.domain.repository;

import com.ciudadania360.subsistemainterno.domain.entity.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DepartamentoRepository extends JpaRepository<Departamento, UUID> {

    // Buscar departamento por nombre
    Departamento findByNombre(String nombre);
}
