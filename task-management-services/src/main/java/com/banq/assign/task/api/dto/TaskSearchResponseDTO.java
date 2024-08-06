package com.banq.assign.task.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskSearchResponseDTO {

    private List<TaskDTO> tasks;
    private int totalPages;

    private long totalElements;

    private int size;

    private boolean first;

    private boolean last;

    private int numberOfElements;
}
