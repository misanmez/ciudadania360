package com.ciudadania360.subsistemainterno.application.mapper;

import com.ciudadania360.subsistemainterno.application.dto.adjunto.AdjuntoRequest;
import com.ciudadania360.subsistemainterno.domain.entity.Adjunto;
import com.ciudadania360.subsistemainterno.domain.entity.Departamento;
import com.ciudadania360.subsistemainterno.application.dto.departamento.*;
import org.mapstruct.*;
import java.util.List;

@Mapper(componentModel = "spring")
public interface DepartamentoMapper {

    Departamento toEntity(DepartamentoRequest request);

    DepartamentoResponse toResponse(Departamento entity);

    List<DepartamentoResponse> toResponseList(List<Departamento> entities);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget Departamento entity, DepartamentoRequest request);

}
