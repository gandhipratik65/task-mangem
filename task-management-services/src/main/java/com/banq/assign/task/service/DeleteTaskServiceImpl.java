package com.banq.assign.task.service;

import com.banq.assign.common.exception.ResourceNotFoundException;
import com.banq.assign.task.repos.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of the {@link DeleteTaskService} interface for handling task deletion.
 */
@Service
public class DeleteTaskServiceImpl implements DeleteTaskService{
    private static final Logger logger = LoggerFactory.getLogger(DeleteTaskServiceImpl.class);

    @Autowired
    private TaskRepository taskRepository;
    /**
     * Deletes a task identified by the given ID.
     *
     * @param id The ID of the task to be deleted.
     */
    @Override
    public void deleteTask(Long id) throws ResourceNotFoundException {
        try {
            taskRepository.deleteById(id);
            logger.info("Task with ID {} successfully deleted.", id);
        }catch (Exception e){
            throw new ResourceNotFoundException("Task is not present");

        }
    }
}
