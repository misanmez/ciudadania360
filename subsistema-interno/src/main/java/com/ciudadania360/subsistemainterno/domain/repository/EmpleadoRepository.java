package com.ciudadania360.subsistemainterno.domain.repository;

import com.ciudadania360.shared.domain.entity.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, UUID> {

    // Buscar empleado por email
    Empleado findByEmail(String email);

    // Buscar empleados por rol
    java.util.List<Empleado> findByRol(String rol);
}
