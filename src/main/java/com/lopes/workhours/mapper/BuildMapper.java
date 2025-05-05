package com.lopes.workhours.mapper;

import com.lopes.workhours.domain.entities.Build;
import com.lopes.workhours.domain.model.BuildModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface BuildMapper {

    BuildModel toModel(Build build);

    @Mappings({
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true)
    })
    Build toEntity(BuildModel model);
}
