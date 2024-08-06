package com.banq.assign.notification.send;

import com.banq.assign.common.exception.EmailNotificationFailedException;

import java.util.Map;
/**
 * Service interface for sending email notifications related to tasks.
 */
public interface EmailSenderService {
    /**
     * Sends an email notification for a task that is due soon.
     *
     * @param variables    A map of variables to be used in the email body, such as task title, description, and due date.
     * @param emailAddress The recipient's email address.
     * @param title        The title of the task.
     * @param userName     The name of the user who is assigned the task.
     */
     void sendTaskDueEmail( Map<String, Object> variables,String emailAddress,String title,String userName) throws EmailNotificationFailedException;
    /**
     * Sends an email notification for a newly assigned task.
     *
     * @param variables    A map of variables to be used in the email body, such as task title, description, and due date.
     * @param emailAddress The recipient's email address.
     * @param title        The title of the task.
     * @param userName     The name of the user who is assigned the task.
     */
     void sendTaskAssignedEmail(Map<String, Object> variables,String emailAddress,String title,String userName) throws EmailNotificationFailedException;
}
