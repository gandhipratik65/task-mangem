package com.banq.assign.task.service;

import com.banq.assign.task.entity.audit.TaskAud;
import com.banq.assign.task.repos.audit.TaskAuditRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * Service implementation for retrieving task audit records.
 */
@Service
public class GetTaskAuditServiceImpl implements GetTaskAuditService{
    private static final Logger logger = LoggerFactory.getLogger(GetTaskAuditServiceImpl.class);

    @Autowired
    private TaskAuditRepository taskAuditRepository;

    /**
     * Retrieves a list of task audit records by the given record ID, sorted by revision in descending order.
     *
     * @param recordId The ID of the record whose audit details are to be retrieved.
     * @return A list of {@link TaskAud} audit records associated with the given record ID.
     */
    @Override
    public List<TaskAud> getTaskByRecordId(Long recordId) {
        logger.debug("Fetching task audit records for record ID: {}", recordId);

        List<TaskAud> taskAudits =  taskAuditRepository.findById(recordId, Sort.by(Sort.Direction.DESC, "rev"));

        if (taskAudits.isEmpty()) {
            logger.info("No task audit records found for record ID: {}", recordId);
        } else {
            logger.info("Found {} task audit records for record ID: {}", taskAudits.size(), recordId);
        }
        return taskAudits;

    }
}
