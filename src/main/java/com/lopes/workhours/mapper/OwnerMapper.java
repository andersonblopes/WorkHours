package com.lopes.workhours.mapper;

import com.lopes.workhours.domain.entities.Owner;
import com.lopes.workhours.domain.model.OwnerModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface OwnerMapper {

    OwnerModel toModel(Owner owner);

    @Mappings({
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true)
    })
    Owner toEntity(OwnerModel model);
}
