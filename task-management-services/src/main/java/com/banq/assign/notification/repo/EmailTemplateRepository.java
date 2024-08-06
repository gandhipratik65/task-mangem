package com.banq.assign.notification.repo;

import com.banq.assign.notification.entity.EmailTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * Repository interface for performing CRUD operations on {@link EmailTemplate} entities.
 */
public interface EmailTemplateRepository extends JpaRepository<EmailTemplate,Long> {
    /**
     * Finds an {@link EmailTemplate} by its name.
     *
     * @param name The name of the email template.
     * @return The {@link EmailTemplate} with the specified name, or {@code null} if no template is found.
     */
    EmailTemplate findByName(String name);
}
