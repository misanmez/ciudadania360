package com.ciudadania360.subsistemainterno.application.mapper;

import com.ciudadania360.subsistemainterno.application.dto.partetrabajo.ParteTrabajoRequest;
import com.ciudadania360.subsistemainterno.application.dto.partetrabajo.ParteTrabajoResponse;
import com.ciudadania360.subsistemainterno.domain.entity.*;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {EmpleadoMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ParteTrabajoMapper {

    @Mapping(target = "adjuntos", ignore = true)
    @Mapping(target = "empleadoAsignado", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    ParteTrabajo toEntity(ParteTrabajoRequest request);

    @Mapping(target = "empleadoAsignado", source = "empleadoAsignado")
    ParteTrabajoResponse toResponse(ParteTrabajo entity);

    @Mapping(target = "adjuntos", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    void updateEntity(@MappingTarget ParteTrabajo entity, ParteTrabajoRequest request);
}
