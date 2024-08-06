package com.banq.assign.task.entity.audit;

import com.banq.assign.task.enums.Status;
import com.banq.assign.user.entity.User;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "task_aud")
@Data
public class TaskAud {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer rev; // The revision number


    private String description;

    @Column(name="due_date")
    private LocalDate dueDate;

    private String priority;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String title;

    @Column(name="created_by")
    private String createdBy;

    @Column(name="created_date")
    private LocalDate createdDate;

    private Boolean deleted;

    @Column(name="last_updated_date")
    private LocalDate lastUpdatedDate;

    @Column(name="updated_by")
    private String updatedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_to")
    private User assignedTo;

}
