package com.banq.assign.notification.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class EmailTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String subject;

    @Lob
    private String body;

    // Constructors, getters, and setters

    public EmailTemplate() {
    }

    public EmailTemplate(String name, String subject, String body) {
        this.name = name;
        this.subject = subject;
        this.body = body;
    }

    // Getters and setters
}

