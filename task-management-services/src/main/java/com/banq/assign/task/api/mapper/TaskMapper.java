package com.banq.assign.task.api.mapper;

import com.banq.assign.task.api.dto.CreateTaskDTO;
import com.banq.assign.task.api.dto.TaskAudDTO;
import com.banq.assign.task.api.dto.TaskDTO;
import com.banq.assign.task.entity.Task;
import com.banq.assign.task.entity.audit.TaskAud;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import java.util.List;

/**
 *
 * Mapper interface for converting between Task entities and DTOs.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
public interface TaskMapper {


    /**
     * Converts a Task entity to a TaskDTO.
     * <p>
     * Maps the 'assignedTo.username' property in Task to 'assignedToName' in TaskDTO.
     *
     * @param task the Task entity to convert
     * @return the corresponding TaskDTO
     */
    @Mapping(source = "assignedTo.username", target = "assignedToName")
    TaskDTO toDto(Task task);

    /**
     * Converts a TaskDTO to a Task entity.
     *
     * @param taskDto the TaskDTO to convert
     * @return the corresponding Task entity
     */
    Task toEntity(TaskDTO taskDto);

    /**
     * Converts a CreateTaskDTO to a Task entity.
     *
     * @param taskDto the CreateTaskDTO to convert
     * @return the corresponding Task entity
     */
    Task toEntityFromCreateTask(CreateTaskDTO taskDto);

    /**
     * Converts a TaskAud entity to a TaskAudDTO.
     * <p>
     * Maps the 'assignedTo.username' property in TaskAud to 'assignedToName' in TaskAudDTO.
     *
     * @param taskAud the TaskAud entity to convert
     * @return the corresponding TaskAudDTO
     */
    @Mapping(source = "assignedTo.username", target = "assignedToName")
    TaskAudDTO toTaskAudDto(TaskAud taskAud);

    /**
     * Converts a list of TaskAud entities to a list of TaskAudDTOs.
     *
     * @param taskAud the list of TaskAud entities to convert
     * @return the list of corresponding TaskAudDTOs
     */
    List<TaskAudDTO> toTaskAudDto(List<TaskAud> taskAud);

    /**
     * Converts a list of Task entities to a list of TaskDTOs.
     *
     * @param task the list of Task entities to convert
     * @return the list of corresponding TaskDTOs
     */
    List<TaskDTO> toDtoList(List<Task> task);


}
