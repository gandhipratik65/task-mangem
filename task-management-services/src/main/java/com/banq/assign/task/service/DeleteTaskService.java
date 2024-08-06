package com.banq.assign.task.service;

import com.banq.assign.common.exception.ResourceNotFoundException;

/**
 * Service interface for deleting tasks.
 */
public interface DeleteTaskService {
    /**
     * Deletes a task identified by the given ID.
     *
     * @param id The ID of the task to be deleted.
     */
    void deleteTask(Long id) throws ResourceNotFoundException;
}
