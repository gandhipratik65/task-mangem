package com.banq.assign.task.api.dto;

import com.banq.assign.task.enums.Status;
import com.banq.assign.task.enums.ValidEnum;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;


@Data
public class TaskDTO {
    @NotNull(message = "ID is mandatory")
    private Long id;

    @NotBlank(message = "Title is mandatory")
    @Size(max = 100, message = "Title cannot exceed 100 characters")
    private String title;

    @NotBlank(message = "Description is mandatory")
    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    @NotNull(message = "Status is mandatory")
    @ValidEnum(enumClass = Status.class, message = "Status must be TODO, IN_PROGRESS, or DONE")
    private Status status;

    @NotBlank(message = "Priority is mandatory")
    @Pattern(regexp = "Low|Medium|High", message = "Priority must be Low, Medium, or High")
    private String priority;

    @NotNull(message = "Due date is mandatory")
    @Future(message = "Due date must be in the future")
    private LocalDate dueDate;

    @NotBlank(message = "Assigned to name is mandatory")
    private String assignedToName;

    // Getters and setters


}
