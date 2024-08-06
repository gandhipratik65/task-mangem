package com.banq.assign.common.audit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;
/**
 * Configuration class for setting up JPA auditing.
 * Provides the current auditor (user) for audit purposes.
 */
@Configuration
@EnableJpaAuditing
public class AuditorConfig {
    /**
     * Default username used when the actual user is not available.
     */
    public static final String SYSTEM_USER = "SYSTEM";

    public AuditorConfig() {
    }

    /**
     * Bean definition for AuditorAware to provide the current auditor's username.
     *
     * @return An AuditorAware instance that returns the current user's username or SYSTEM if not available.
     */
    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> {
            return SecurityContextHolder.getContext().getAuthentication() != null ? Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication().getName()) : Optional.of("SYSTEM");
        };
    }
}