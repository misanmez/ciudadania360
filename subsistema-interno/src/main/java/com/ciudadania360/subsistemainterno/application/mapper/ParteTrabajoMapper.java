package com.ciudadania360.subsistemainterno.application.mapper;

import com.ciudadania360.subsistemainterno.application.dto.partetrabajo.ParteTrabajoRequest;
import com.ciudadania360.subsistemainterno.application.dto.partetrabajo.ParteTrabajoResponse;
import com.ciudadania360.subsistemainterno.domain.entity.*;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {EmpleadoMapper.class})
public interface ParteTrabajoMapper {

    ParteTrabajo toEntity(ParteTrabajoRequest request);

    @Mapping(target = "empleadoAsignado", source = "empleadoAsignado")
    ParteTrabajoResponse toResponse(ParteTrabajo entity);

    @Mapping(target = "empleadoAsignado", ignore = true)
    void updateEntity(@MappingTarget ParteTrabajo entity, ParteTrabajoRequest request);
}
