package com.ciudadania360.gestionrolespermisos.application.mapper;

import com.ciudadania360.gestionrolespermisos.application.dto.usuariorol.UsuarioRolRequest;
import com.ciudadania360.gestionrolespermisos.application.dto.usuariorol.UsuarioRolResponse;
import com.ciudadania360.gestionrolespermisos.domain.entity.UsuarioRol;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UsuarioRolMapper {

    UsuarioRol toEntity(UsuarioRolRequest request);

    UsuarioRolResponse toResponse(UsuarioRol entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget UsuarioRol entity, UsuarioRolRequest request);
}

