package com.lopes.workhours.mapper;

import com.lopes.workhours.domain.entities.WorkLog;
import com.lopes.workhours.domain.model.WorkLogModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {EmployeeMapper.class, ApartmentMapper.class, UserMapper.class})
public interface WorkLogMapper {

    WorkLogModel toModel(WorkLog entity);

    @Mapping(target = "total", ignore = true) // Entity doesn't store it
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    WorkLog toEntity(WorkLogModel model);

}
