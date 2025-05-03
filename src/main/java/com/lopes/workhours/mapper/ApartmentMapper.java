package com.lopes.workhours.mapper;

import com.lopes.workhours.domain.entities.Apartment;
import com.lopes.workhours.domain.model.ApartmentModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {BuildMapper.class})
public interface ApartmentMapper {

    ApartmentModel toModel(Apartment apartment);

    @Mappings({
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
            @Mapping(target = "descriptionFormated", ignore = true)
    })
    Apartment toEntity(ApartmentModel model);
}
