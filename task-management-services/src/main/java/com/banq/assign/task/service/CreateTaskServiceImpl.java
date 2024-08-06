package com.banq.assign.task.service;

import com.banq.assign.task.entity.Task;
import com.banq.assign.task.repos.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of the CreateTaskService interface for creating tasks.
 */
@Service
public class CreateTaskServiceImpl implements CreateTaskService{
    private static final Logger logger = LoggerFactory.getLogger(CreateTaskServiceImpl.class);

    @Autowired
    private TaskRepository taskRepository;

    /**
     * Creates a new task, sets its status to TODO,
     * and saves it to the repository.
     *
     * @param task The task to be created.
     * @return The created task.
     */
    @Override
    public Task createTask(Task task) {
        // Set the initial status of the task to TODO
        task.setStatus(Task.Status.TODO);
        // Save the task to the repository
        Task createdTask = taskRepository.save(task);

        // Log the creation of the task
        logger.info("Task created with ID: {}", createdTask.getId());

        return createdTask;
    }


}
