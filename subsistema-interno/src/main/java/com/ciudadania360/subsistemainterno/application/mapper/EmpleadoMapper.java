package com.ciudadania360.subsistemainterno.application.mapper;

import com.ciudadania360.shared.domain.entity.Empleado;
import com.ciudadania360.subsistemainterno.application.dto.empleado.*;
import org.mapstruct.*;
import java.util.List;

@Mapper(componentModel = "spring")
public interface EmpleadoMapper {

    Empleado toEntity(EmpleadoRequest request);

    EmpleadoResponse toResponse(Empleado entity);

    List<EmpleadoResponse> toResponseList(List<Empleado> entities);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget Empleado entity, EmpleadoRequest request);

}
