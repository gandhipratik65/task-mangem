package com.banq.assign.notification.send;

import com.banq.assign.common.exception.EmailNotificationFailedException;
import com.banq.assign.notification.entity.EmailTemplate;
import com.banq.assign.notification.repo.EmailTemplateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Map;

/**
 * Implementation of the EmailSenderService interface for sending email notifications.
 */
@Service
public class EmailSenderServiceImpl implements EmailSenderService {

    private static final Logger logger = LoggerFactory.getLogger(EmailSenderServiceImpl.class);
    // Template names for email notifications
    public static final String TASK_DUE_REMINDER = "taskDueReminder";
    private static final String TASK_ASSIGNED_NOTIFICATION = "taskAssignedNotification";
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmailTemplateRepository emailTemplateRepository;

    /**
     * Sends an email notification for a task that is due soon.
     *
     * @param variables    A map of variables to be used in the email body.
     * @param emailAddress The recipient's email address.
     * @param title        The title of the task.
     * @param userName     The name of the user who is assigned the task.
     */
    @Override
    public void sendTaskDueEmail( Map<String, Object> variables,String emailAddress,String title,String userName) throws EmailNotificationFailedException {
        EmailTemplate template = emailTemplateRepository.findByName(TASK_DUE_REMINDER);

        String subject = template.getSubject();
        String body = template.getBody();

        // Replace variables in the body
        for (Map.Entry<String, Object> entry : variables.entrySet()) {
            body = body.replace("{{" + entry.getKey() + "}}", entry.getValue().toString());
        }
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(emailAddress);
            helper.setSubject(subject);
            helper.setText("Dear " + userName + ",\n\n" + body + "\n\nBest regards,\nYour Task Management System");


            mailSender.send(message);
            logger.info("Task due email sent to {}", emailAddress);

        } catch (MessagingException e) {
            logger.error("Error sending task assigned email to {}: {}", emailAddress, e.getMessage());
            throw new EmailNotificationFailedException("Email Notification failed.");
        }
    }
    /**
     * Sends an email notification for a newly assigned task.
     *
     * @param variables    A map of variables to be used in the email body.
     * @param emailAddress The recipient's email address.
     * @param title        The title of the task.
     * @param userName     The name of the user who is assigned the task.
     */
    @Override
    public void sendTaskAssignedEmail(Map<String, Object> variables,String emailAddress,String title,String userName) throws EmailNotificationFailedException {
        EmailTemplate template = emailTemplateRepository.findByName(TASK_ASSIGNED_NOTIFICATION);

        String subject = template.getSubject();
        String body = template.getBody();

        // Replace variables in the body
        for (Map.Entry<String, Object> entry : variables.entrySet()) {
            body = body.replace("{{" + entry.getKey() + "}}", entry.getValue().toString());
        }

        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(emailAddress);
            helper.setSubject(subject );
            helper.setText("Dear " + userName + ",\n\n" + body + "\n\nBest regards,\nYour Task Management System");

            mailSender.send(message);
            logger.info("Task assigned email sent to {}", emailAddress);

        } catch (MessagingException e) {
            logger.error("Error sending task assigned email to {}: {}", emailAddress, e.getMessage());
            throw new EmailNotificationFailedException("Email Notification failed.");

        }
    }
}
