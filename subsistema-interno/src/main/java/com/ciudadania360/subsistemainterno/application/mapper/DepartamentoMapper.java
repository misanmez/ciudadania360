package com.ciudadania360.subsistemainterno.application.mapper;

import com.ciudadania360.subsistemainterno.domain.entity.Departamento;
import com.ciudadania360.subsistemainterno.application.dto.departamento.*;
import org.mapstruct.*;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DepartamentoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    Departamento toEntity(DepartamentoRequest request);

    DepartamentoResponse toResponse(Departamento entity);

    List<DepartamentoResponse> toResponseList(List<Departamento> entities);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    void updateEntity(@MappingTarget Departamento entity, DepartamentoRequest request);

}
