package com.ciudadania360.subsistemainterno.domain.repository;

import com.ciudadania360.subsistemainterno.domain.entity.ParteTrabajo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ParteTrabajoRepository extends JpaRepository<ParteTrabajo, UUID> {

    // Buscar partes de trabajo por estado
    List<ParteTrabajo> findByEstado(String estado);

    // Buscar partes de trabajo asignadas a un empleado
    List<ParteTrabajo> findByEmpleadoAsignado_Id(UUID empleadoId);

    // Buscar partes de trabajo derivadas de una solicitud
    List<ParteTrabajo> findBySolicitudId(UUID solicitudId);
}
