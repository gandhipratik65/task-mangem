package com.banq.assign.task.api.service;

import com.banq.assign.common.exception.EmailNotificationFailedException;
import com.banq.assign.common.exception.InvalidIdException;
import com.banq.assign.task.api.dto.CreateTaskDTO;
import com.banq.assign.common.exception.ResourceNotFoundException;
import com.banq.assign.task.api.dto.TaskAudDTO;
import com.banq.assign.task.api.dto.TaskDTO;
import com.banq.assign.task.api.dto.TaskSearchResponseDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TaskApplicationService {
    List<TaskDTO> getAllTasks();

   TaskDTO getTaskById(Long id) throws ResourceNotFoundException, InvalidIdException;

    TaskDTO createTask(CreateTaskDTO task) throws ResourceNotFoundException, EmailNotificationFailedException;

    TaskDTO updateTask( TaskDTO taskDetails) throws ResourceNotFoundException;

    void deleteTask(Long id) throws InvalidIdException, ResourceNotFoundException;

    List<TaskAudDTO> getHistoryTaskById(Long id) throws InvalidIdException;

    TaskSearchResponseDTO searchTask(TaskDTO taskDetails, Pageable pageable);
}
