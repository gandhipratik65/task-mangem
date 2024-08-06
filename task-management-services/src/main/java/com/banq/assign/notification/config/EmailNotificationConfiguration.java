package com.banq.assign.notification.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
/**
 * Configuration class for setting up email notifications.
 */
@Configuration
public class EmailNotificationConfiguration {

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.port}")
    private int port;

    @Value("${spring.mail.protocol}")
    private String protocol;

    @Value("${spring.mail.default-encoding}")
    private String defaultEncoding;


    /**
     * Configures and provides a {@link JavaMailSender} bean for sending emails.
     *
     * @return A configured {@link JavaMailSender} instance.
     */
    @Bean
    public JavaMailSender mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        // Set the SMTP server properties
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setProtocol(protocol);
        mailSender.setDefaultEncoding(defaultEncoding);

        return mailSender;
    }
}
