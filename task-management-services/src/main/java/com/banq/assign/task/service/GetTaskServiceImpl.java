package com.banq.assign.task.service;

import com.banq.assign.common.exception.ResourceNotFoundException;
import com.banq.assign.task.entity.Task;
import com.banq.assign.task.repos.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
/**
 * Implementation of {@link GetTaskService} for managing tasks.
 */
@Service
public class GetTaskServiceImpl implements GetTaskService{
    private static final Logger logger = LoggerFactory.getLogger(GetTaskServiceImpl.class);

    @Autowired
    private TaskRepository taskRepository;
    /**
     * Retrieves all tasks. This method currently returns null; consider implementing it as needed.
     *
     * @return A list of all {@link Task} objects.
     */
    @Override
    public List<Task> getAllTasks() {
        logger.debug("Retrieving all tasks");
        return taskRepository.findAll();
    }

    /**
     * Retrieves a task by its ID.
     *
     * @param id The ID of the task to be retrieved.
     * @return The {@link Task} object associated with the given ID.
     * @throws ResourceNotFoundException if no task is found with the given ID.
     */
    @Override
    public Task getTaskById(Long id) throws ResourceNotFoundException {
        logger.debug("Retrieving task with ID: {}", id);

        Optional<Task> optionalTask = taskRepository.findById(id);
        if(optionalTask.isPresent()){
            return optionalTask.get();
        }
        logger.error("Task with ID: {} not found", id);
        throw new ResourceNotFoundException("Task is not present");
    }

    /**
     * Retrieves a paginated list of tasks based on the specified criteria and pagination settings.
     *
     * @param taskSpecification The criteria to filter tasks.
     * @param pageable The pagination settings.
     * @return A {@link Page} of {@link Task} objects matching the criteria.
     */
    @Override
    public Page<Task> getTaskBySpecificationAndPageable(Specification<Task> taskSpecification, Pageable pageable) {
        logger.debug("Retrieving tasks with specification and pageable settings");
        return taskRepository.findAll(taskSpecification,pageable);
    }
}
