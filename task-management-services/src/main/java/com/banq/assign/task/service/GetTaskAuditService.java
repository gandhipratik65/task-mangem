package com.banq.assign.task.service;

import com.banq.assign.task.entity.audit.TaskAud;

import java.util.List;
/**
 * Service interface for retrieving task audit records.
 */
public interface GetTaskAuditService {
    /**
     * Retrieves a list of task audit records by the given record ID.
     *
     * @param recordId The ID of the record whose audit details are to be retrieved.
     * @return A list of {@link TaskAud} audit records associated with the given record ID.
     */
    List<TaskAud> getTaskByRecordId(Long recordId);

}
