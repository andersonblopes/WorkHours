package com.lopes.workhours.mapper;

import com.lopes.workhours.domain.entities.Employee;
import com.lopes.workhours.domain.model.EmployeeModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface EmployeeMapper {

    EmployeeModel toModel(Employee entity);

    @Mappings({
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true)
    })
    Employee toEntity(EmployeeModel model);
}
