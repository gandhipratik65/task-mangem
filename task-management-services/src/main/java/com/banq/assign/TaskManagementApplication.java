package com.banq.assign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/**
 *   The entry point for the Task Management application.
 *   @SpringBootApplication to mark it as a Spring Boot application and enable component scanning, auto-configuration, and property support.
 *   @EnableWebSecurity to enable Spring Security configuration
 *   @EnableScheduling to enable Spring's scheduling
 */
@SpringBootApplication
@EnableWebSecurity
@EnableScheduling
public class TaskManagementApplication
{
    /**
     * The main method serves as the entry point for the application.
     * @param args
     */
    public static void main( String[] args )
    {

        SpringApplication.run(TaskManagementApplication.class, args);
    }


}
