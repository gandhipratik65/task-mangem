package com.banq.assign.task.api.service;

import com.banq.assign.common.exception.EmailNotificationFailedException;
import com.banq.assign.common.exception.InvalidIdException;
import com.banq.assign.task.api.dto.CreateTaskDTO;
import com.banq.assign.task.api.mapper.TaskMapper;
import com.banq.assign.notification.send.EmailSenderServiceImpl;
import com.banq.assign.task.api.dto.TaskAudDTO;
import com.banq.assign.task.api.dto.TaskSearchResponseDTO;
import com.banq.assign.task.api.dto.TaskDTO;
import com.banq.assign.common.exception.ResourceNotFoundException;
import com.banq.assign.task.entity.Task;
import com.banq.assign.task.entity.audit.TaskAud;
import com.banq.assign.task.service.*;

import com.banq.assign.user.service.GetUserService;
import com.sun.mail.util.MailConnectException;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of the TaskApplicationService interface.
 * <p>
 * This service handles task-related operations including task creation, update, retrieval, and deletion.
 * It also manages task assignment and history retrieval.
 * Email notifications are sent for task assignments.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TaskApplicationServiceImpl implements TaskApplicationService{
    private static final Logger logger = LoggerFactory.getLogger(TaskApplicationServiceImpl.class);

    private final TaskMapper mapper = Mappers.getMapper(TaskMapper.class);

    @Autowired
    private CreateTaskService createTaskService;

    @Autowired
    private UpdateTaskService updateTaskService;

    @Autowired
    private DeleteTaskService deleteTaskService;

    @Autowired
    private GetTaskService getTaskService;

    @Autowired
    private GetUserService getUserService;

    @Autowired
    private GetTaskAuditService getTaskAuditService;

    @Autowired
    private EmailSenderServiceImpl eMailSenderService;
    /**
     * Retrieves all tasks. Currently not implemented.
     *
     * @return List of TaskDTO
     */
    @Override
    public List<TaskDTO> getAllTasks() {
        logger.debug("Fetching all tasks");
        List<Task> taskList = getTaskService.getAllTasks();
        return mapper.toDtoList(taskList);
    }
    /**
     * Retrieves a task by its ID.
     *
     * @param id the ID of the task to retrieve
     * @return TaskDTO representation of the task
     * @throws ResourceNotFoundException if the task is not found
     */
    @Override
    public TaskDTO getTaskById(Long id) throws ResourceNotFoundException, InvalidIdException {
        logger.debug("Fetching task with ID: {}", id);
        validateId(id);
        Task optionalTask = getTaskService.getTaskById(id);
         return mapper.toDto(optionalTask);
    }

    /**
     * Validate id and throw exception
     * @param id
     * @throws InvalidIdException
     */
    private  void validateId(Long id) throws InvalidIdException {
        if (id == null || id <= 0) {
            throw new InvalidIdException("Invalid task ID provided");
        }
    }

    /**
     * Creates a new task and sends an email notification upon creation.
     *
     * @param taskDTO the details of the task to create
     * @return TaskDTO representation of the created task
     * @throws ResourceNotFoundException if the assigned user is not found
     */
    @Override
    public TaskDTO createTask(CreateTaskDTO taskDTO) throws ResourceNotFoundException, EmailNotificationFailedException {
        logger.debug("Creating task with details: {}", taskDTO);
        Task task = mapper.toEntityFromCreateTask(taskDTO);
        task.setAssignedTo(getUserService.getUserByUserName(taskDTO.getAssignedToName()));
        Task createdTask = createTaskService.createTask(task);
        Map<String,Object> variableMap = new HashMap<>();
        variableMap.put("taskTitle", task.getTitle());
        variableMap.put("taskDescription", task.getDescription());
        variableMap.put("dueDate", task.getDueDate());
        eMailSenderService.sendTaskAssignedEmail(variableMap,createdTask.getAssignedTo().getEmailAddress(),createdTask.getTitle(),createdTask.getAssignedTo().getUsername());
        return mapper.toDto(createdTask);

    }
    /**
     * Updates an existing task.
     *
     * @param taskDetails the updated details of the task
     * @return TaskDTO representation of the updated task
     */
    @Override
    public TaskDTO updateTask(TaskDTO taskDetails) throws ResourceNotFoundException {
        logger.debug("Updating task with details: {}", taskDetails);
        Task task = mapper.toEntity(taskDetails);
        task.setAssignedTo(getUserService.getUserByUserName(taskDetails.getAssignedToName()));
        Task createdTask = updateTaskService.updateTask(task);
        return mapper.toDto(createdTask);
    }
    /**
     * Deletes a task by its ID.
     *
     * @param id the ID of the task to delete
     */
    @Override
    public void deleteTask(Long id) throws InvalidIdException, ResourceNotFoundException {
        logger.debug("Deleting task with ID: {}", id);
        validateId(id);
        deleteTaskService.deleteTask(id);

    }
    /**
     * Retrieves the history of a task by its ID.
     *
     * @param id the ID of the task
     * @return List of TaskAudDTO representing the task's history
     */
    @Override
    public List<TaskAudDTO> getHistoryTaskById(Long id) throws InvalidIdException {
        logger.debug("Fetching history for task with ID: {}", id);
        validateId(id);
        List<TaskAud> taskAudList = getTaskAuditService.getTaskByRecordId(id);
       List<TaskAudDTO> taskAudDTOList = mapper.toTaskAudDto(taskAudList);
        return taskAudDTOList;
    }
    /**
     * Searches for tasks based on given criteria and pagination.
     *
     * @param taskDetails the criteria to search for
     * @param pageable    pagination information
     * @return TaskSearchResponseDTO containing search results and pagination info
     */
    @Override
    public TaskSearchResponseDTO searchTask(TaskDTO taskDetails, Pageable pageable) {
        logger.debug("Searching tasks with criteria: {}", taskDetails);

        Pageable newPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());


        TaskSearchResponseDTO taskSearchResponseDTO ;
        Page<Task> taskPage = getTaskService.getTaskBySpecificationAndPageable((Specification<Task>) (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (taskDetails.getTitle() != null && !taskDetails.getTitle().isEmpty()) {
                predicates.add(cb.like(root.get("title"), "%" + taskDetails.getTitle() + "%"));
            }
            if (taskDetails.getDescription() != null && !taskDetails.getDescription().isEmpty()) {
                predicates.add(cb.like(root.get("description"), "%" + taskDetails.getDescription() + "%"));
            }
            if (taskDetails.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), taskDetails.getStatus()));
            }
            if (taskDetails.getPriority() != null && !taskDetails.getPriority().isEmpty()) {
                predicates.add(cb.equal(root.get("priority"), taskDetails.getPriority()));
            }
            if (taskDetails.getDueDate() != null) {
                predicates.add(cb.equal(root.get("dueDate"), taskDetails.getDueDate()));
            }
            if (taskDetails.getAssignedToName() != null && !taskDetails.getAssignedToName().isEmpty()) {
                predicates.add(cb.like(root.join("assignedTo").get("username"), "%" + taskDetails.getAssignedToName() + "%"));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        }, newPageable);

        List<TaskDTO> taskDTOS = mapper.toDtoList(taskPage.getContent());
        taskSearchResponseDTO = TaskSearchResponseDTO.builder().first(taskPage.isFirst()).last(taskPage.isLast())
                .numberOfElements(taskPage.getNumberOfElements()).totalElements(taskPage.getTotalElements())
                .totalPages(taskPage.getTotalPages()).tasks(taskDTOS).build();

        return taskSearchResponseDTO;
    }
}
