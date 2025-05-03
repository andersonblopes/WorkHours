package com.lopes.workhours.mapper;

import com.lopes.workhours.domain.entities.User;
import com.lopes.workhours.domain.model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings({
            @Mapping(target = "confirmPassword", ignore = true) // Entity doesn't have it
    })
    UserModel toModel(User entity);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
            @Mapping(target = "uuid", ignore = true),
            @Mapping(target = "version", ignore = true)
    })
    User toEntity(UserModel model);
}
