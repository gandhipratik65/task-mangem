package com.banq.assign.task.scheduler;


import com.banq.assign.common.exception.EmailNotificationFailedException;
import com.banq.assign.notification.send.EmailSenderService;
import com.banq.assign.task.entity.Task;
import com.banq.assign.task.repos.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Service for scheduling and sending task-related email notifications.
 */
@Service
@Transactional
public class TaskNotificationSchedulerServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(TaskNotificationSchedulerServiceImpl.class);

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private EmailSenderService mailSender;

    /**
     * Scheduled task that checks for tasks due in 1 day and sends notification emails.
     * This method is currently commented out but should be scheduled to run daily.
     */
   // @Scheduled(cron = "* * * * * *") // Runs at midnight every day
    public void checkTasksAndSendEmails() throws EmailNotificationFailedException {
        List<Task> tasks = taskRepository.findAll();
        LocalDate today = LocalDate.now();

        for (Task task : tasks) {
            if (ChronoUnit.DAYS.between(today, task.getDueDate()) == 1) {
                Map<String, Object> variables = new HashMap<>();
                variables.put("userName",task.getAssignedTo().getUsername());
                variables.put("taskTitle",task.getTitle());
                variables.put("dueDate",task.getDueDate());

                variables.put("taskDescription",task.getDescription());

                variables.put("emailAddress",task.getAssignedTo().getEmailAddress());

                // Send email notification
                mailSender.sendTaskDueEmail(variables,task.getAssignedTo().getEmailAddress(),task.getTitle(),task.getAssignedTo().getUsername());
                logger.info("Email sent to {} for task {}", task.getAssignedTo().getEmailAddress(), task.getTitle());

            }
        }
    }
}
