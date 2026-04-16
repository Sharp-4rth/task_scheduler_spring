package com.scheduler.dtos;

import com.scheduler.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    TaskDTO toDTO(Task task);

    @Mapping(target = "id", ignore = true)      // protects id and status
    @Mapping(target = "status", ignore = true)
    Task toEntity(CreateTaskRequest dto);
}
