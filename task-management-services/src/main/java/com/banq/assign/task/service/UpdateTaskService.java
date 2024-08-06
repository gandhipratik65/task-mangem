package com.banq.assign.task.service;

import com.banq.assign.common.exception.ResourceNotFoundException;
import com.banq.assign.task.entity.Task;
/**
 * Service interface for updating {@link Task} entities.
 */
public interface UpdateTaskService {
    /**
     * Updates the given {@link Task} entity.
     *
     * @param task The {@link Task} entity with updated information.
     * @return The updated {@link Task} entity.
     */
    Task updateTask( Task task) throws ResourceNotFoundException;

}
