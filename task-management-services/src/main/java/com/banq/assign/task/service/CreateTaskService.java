package com.banq.assign.task.service;

import com.banq.assign.task.entity.Task;

/**
 * Service interface for creating tasks.
 */
public interface CreateTaskService {

    /**
     * Creates a new task.
     *
     * @param task The task to be created.
     * @return The created task.
     */
    Task createTask(Task task);


}
