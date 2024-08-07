package com.banq.assign.task.api.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.BDDMockito.*;

import com.banq.assign.TaskManagementApplication;
import com.banq.assign.common.exception.EmailNotificationFailedException;
import com.banq.assign.common.exception.InvalidIdException;
import com.banq.assign.common.exception.ResourceNotFoundException;
import com.banq.assign.task.api.dto.CreateTaskDTO;
import com.banq.assign.task.api.dto.TaskAudDTO;
import com.banq.assign.task.api.dto.TaskDTO;
import com.banq.assign.task.api.dto.TaskSearchResponseDTO;
import com.banq.assign.task.api.service.TaskApplicationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
@SpringBootTest(classes = TaskManagementApplication.class)
@AutoConfigureMockMvc
public class TaskControllerTest {

    public static final String ADMIN = "ADMIN";
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskApplicationService taskService;

    @BeforeEach
    public void setUp() {
        // Set up mock behavior here if needed
    }

    @Test
    @WithMockUser(roles = ADMIN)
    public void testGetAllTasks() throws Exception {
        TaskDTO task = new TaskDTO();
        task.setId(1L);
        task.setTitle("Test Task");
        List<TaskDTO> tasks = Collections.singletonList(task);

        given(taskService.getAllTasks()).willReturn(tasks);

        mockMvc.perform(get("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("Test Task"));
    }

    @Test
    @WithMockUser(roles = ADMIN)
    public void testGetTaskById() throws Exception, ResourceNotFoundException, InvalidIdException {
        TaskDTO task = new TaskDTO();
        task.setId(1L);
        task.setTitle("Test Task");

        given(taskService.getTaskById(1L)).willReturn(task);

        mockMvc.perform(get("/api/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Test Task"));
    }

    @Test
    @WithMockUser(roles = ADMIN)
    public void testCreateTask() throws Exception, EmailNotificationFailedException, ResourceNotFoundException {
        CreateTaskDTO createTaskDTO = new CreateTaskDTO();
        createTaskDTO.setTitle("New Task");

        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(1L);
        taskDTO.setTitle("New Task");

        given(taskService.createTask(any(CreateTaskDTO.class))).willReturn(taskDTO);

        String jsonContent = "{" +
                "\"title\":\"New Task\"," +
                "\"description\":\"Task Description\"," +
                "\"priority\":\"High\"," +
                "\"dueDate\":\"" + LocalDate.now().plusDays(1).toString() + "\"," +
                "\"assignedToName\":\"John Doe\"" +
                "}";
        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("New Task"));
    }

    @Test
    @WithMockUser(roles = ADMIN)
    public void testUpdateTask() throws Exception, ResourceNotFoundException {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(1L);
        taskDTO.setTitle("Updated Task");

        given(taskService.updateTask(any(TaskDTO.class))).willReturn(taskDTO);

        String jsonContent = "{\n" +
                "    \"id\": 1,\n" +
                "    \"title\": \"Complete project documentation\",\n" +
                "    \"description\": \"Finalize and submit the documentation for the project.\",\n" +
                "    \"status\": \"IN_PROGRESS\",\n" +
                "    \"priority\": \"High\",\n" +
                "    \"dueDate\": \"2024-08-20\",\n" +
                "    \"assignedToName\": \"Jane Doe\"\n" +
                "}";
        mockMvc.perform(put("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Updated Task"));
    }

    @Test
    @WithMockUser(roles = ADMIN)
    public void testDeleteTask() throws Exception, ResourceNotFoundException, InvalidIdException {
        willDoNothing().given(taskService).deleteTask(1L);

        mockMvc.perform(delete("/api/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = ADMIN)
    public void testGetHistoryTask() throws Exception, InvalidIdException {
        TaskAudDTO taskAudDTO = new TaskAudDTO();
        taskAudDTO.setId(1L);

        List<TaskAudDTO> taskAudDTOList = Collections.singletonList(taskAudDTO);

        given(taskService.getHistoryTaskById(1L)).willReturn(taskAudDTOList);

        mockMvc.perform(get("/api/tasks/history/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    @WithMockUser(roles = ADMIN)
    public void testSearchTask() throws Exception {
        TaskSearchResponseDTO responseDTO = new TaskSearchResponseDTO();
        // Populate responseDTO as needed

        given(taskService.searchTask(any(TaskDTO.class), any(Pageable.class))).willReturn(responseDTO);

        mockMvc.perform(post("/api/tasks/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Search Task\"}"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = ADMIN)
    public void testCreateTaskValidationFailure() throws Exception {
        // Invalid CreateTaskDTO (title is blank, which should trigger validation error)
        String invalidJsonContent = "{" +

                "}";

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJsonContent))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.errors.title").value("Title is mandatory"))
                .andExpect(jsonPath("$.errors.priority").value("Priority is mandatory"))
                .andExpect(jsonPath("$.errors.assignedToName").value("Assigned to name is mandatory"))
                .andExpect(jsonPath("$.errors.dueDate").value("Due date is mandatory"))

        ;
    }
}