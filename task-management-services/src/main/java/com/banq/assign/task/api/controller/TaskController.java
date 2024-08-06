package com.banq.assign.task.api.controller;

import com.banq.assign.common.exception.EmailNotificationFailedException;
import com.banq.assign.task.api.dto.CreateTaskDTO;
import com.banq.assign.task.api.dto.TaskDTO;
import com.banq.assign.common.exception.InvalidIdException;
import com.banq.assign.task.api.service.TaskApplicationService;
import com.banq.assign.task.api.dto.TaskAudDTO;
import com.banq.assign.task.api.dto.TaskSearchResponseDTO;
import com.banq.assign.common.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
/**
 * Controller for managing tasks.
 * Provides endpoints for creating, retrieving, updating, and deleting tasks.
 */
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    private TaskApplicationService taskService;
    /**
     * Retrieves a list of all tasks.
     *
     * @return List of TaskDTO objects
     */
    @GetMapping
    public List<TaskDTO> getAllTasks() {
        logger.info("Retrieving all tasks.");
        return taskService.getAllTasks();
    }

    /**
     * Retrieves a task by its ID.
     *
     * @param id the ID of the task to retrieve
     * @return ResponseEntity with TaskDTO object if found, otherwise not found
     * @throws ResourceNotFoundException if the task is not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long id) throws ResourceNotFoundException, InvalidIdException {
        logger.info("Retrieving task with ID: {}", id);
        TaskDTO task = taskService.getTaskById(id);
        return ResponseEntity.ok(task);

    }

    /**
     * Creates a new task.
     *
     * @param task the task details to create
     * @return TaskDTO object of the created task
     * @throws ResourceNotFoundException if there is an error during task creation
     */
    @PostMapping
    public TaskDTO createTask(@RequestBody @Valid CreateTaskDTO task) throws ResourceNotFoundException, EmailNotificationFailedException {
        logger.info("Creating new task: {}", task);
        return taskService.createTask(task);
    }

    /**
     * Updates an existing task by its ID.
     *
     * @param taskDetails the updated task details
     * @return ResponseEntity with the updated TaskDTO object
     */
    @PutMapping
    public ResponseEntity<TaskDTO> updateTask( @RequestBody @Valid TaskDTO taskDetails) throws ResourceNotFoundException {
        logger.info("Updating task with details: {}", taskDetails);
        TaskDTO updatedTask = taskService.updateTask(taskDetails);
        return ResponseEntity.ok(updatedTask);

    }

    /**
     * Deletes a task by its ID.
     *
     * @param id the ID of the task to delete
     * @return ResponseEntity with no content if deletion is successful, otherwise not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) throws InvalidIdException, ResourceNotFoundException {

            logger.info("Deleting task with ID: {}", id);
            taskService.deleteTask(id);
            return ResponseEntity.noContent().build();

    }

    /**
     * Retrieves the history of a task by its ID.
     *
     * @param id the ID of the task to retrieve history for
     * @return ResponseEntity with a list of TaskAudDTO objects if found, otherwise not found
     */
    @GetMapping("/history/{id}")
    public ResponseEntity<List<TaskAudDTO>> getHistoryTask(@PathVariable Long id) throws InvalidIdException {
        logger.info("Retrieving history for task with ID: {}", id);
        List<TaskAudDTO> taskAudDTOS = taskService.getHistoryTaskById(id);
        return ResponseEntity.ok(taskAudDTOS);

    }

    /**
     * Searches for tasks based on the provided search criteria.
     *
     * @param taskDetails the search criteria
     * @param pageable pagination information
     * @return ResponseEntity with TaskSearchResponseDTO containing search results
     */
    @PostMapping("/search")
    public ResponseEntity<TaskSearchResponseDTO> searchTask(@RequestBody TaskDTO taskDetails, @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        logger.info("Searching tasks with details: {} and pageable: {}", taskDetails, pageable);
        TaskSearchResponseDTO taskSearchResponseDTOS = taskService.searchTask(taskDetails,pageable);
        return ResponseEntity.ok(taskSearchResponseDTOS);

    }
}
