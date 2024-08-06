package com.banq.assign.task.service;

import com.banq.assign.common.exception.ResourceNotFoundException;
import com.banq.assign.task.api.mapper.TaskMapper;
import com.banq.assign.task.entity.Task;
import com.banq.assign.task.repos.TaskRepository;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service implementation for updating {@link Task} entities.
 */
@Service
public class UpdateTaskServiceImpl implements UpdateTaskService{
    private static final Logger logger = LoggerFactory.getLogger(UpdateTaskServiceImpl.class);

    private final TaskMapper mapper = Mappers.getMapper(TaskMapper.class);

    @Autowired
    private TaskRepository taskRepository;
    /**
     * Updates the given {@link Task} entity in the database.
     *
     * @param task The {@link Task} entity with updated information.
     * @return The updated {@link Task} entity.
     */
    @Override
    public Task updateTask(Task task) throws ResourceNotFoundException {
        logger.info("Updating task with ID: {}", task.getId());
        Optional<Task> updatedTaskOp = taskRepository.findById(task.getId());
        if(updatedTaskOp.isPresent()){
            Task actualTask = updatedTaskOp.get();
            actualTask.setAssignedTo(task.getAssignedTo());
            actualTask.setStatus(task.getStatus());
            actualTask.setDescription(task.getDescription());
            actualTask.setPriority(task.getPriority());
            actualTask.setDueDate(task.getDueDate());
            actualTask.setTitle(task.getTitle());
            taskRepository.save(actualTask);
            return actualTask;

        }
        logger.info("Task updated successfully with ID: {}", task.getId());
        throw new ResourceNotFoundException("Task is not present");

    }
}
