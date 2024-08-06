package com.banq.assign.task.service;

import com.banq.assign.common.exception.ResourceNotFoundException;
import com.banq.assign.task.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
/**
 * Service interface for retrieving and managing tasks.
 */
public interface GetTaskService {
    /**
     * Retrieves a list of all tasks.
     *
     * @return A list of {@link Task} objects representing all tasks.
     */
    List<Task> getAllTasks();
    /**
     * Retrieves a task by its ID.
     *
     * @param id The ID of the task to be retrieved.
     * @return The {@link Task} object associated with the given ID.
     * @throws ResourceNotFoundException if no task is found with the given ID.
     */
    Task getTaskById(Long id) throws ResourceNotFoundException;
    /**
     * Retrieves a paginated list of tasks based on the specified criteria and pagination settings.
     *
     * @param specification The criteria to filter tasks.
     * @param pageable The pagination settings.
     * @return A {@link Page} of {@link Task} objects matching the criteria.
     */
    Page<Task> getTaskBySpecificationAndPageable(Specification<Task> specification, Pageable pageable);

}
