package com.banq.assign.task.api.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
public class CreateTaskDTO {

    @NotBlank(message = "Title is mandatory")
    private String title;

    @NotBlank(message = "Description is mandatory")
    private String description;

    @NotBlank(message = "Priority is mandatory")
    @Pattern(regexp = "Low|Medium|High", message = "Priority must be Low, Medium, or High")
    private String priority;

    @NotNull(message = "Due date is mandatory")
    private LocalDate dueDate;

    @NotBlank(message = "Assigned to name is mandatory")
    private String assignedToName;
}
