package com.banq.assign.task.api.dto;

import com.banq.assign.task.enums.Status;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskAudDTO {


    private Long id;


    private String description;

    private LocalDate dueDate;

    private String priority;

    private Status status;

    private String title;

    private String createdBy;

    private LocalDate createdDate;

    private Boolean deleted;

    private LocalDate lastUpdatedDate;

    private String updatedBy;

    private String assignedToName;
}
