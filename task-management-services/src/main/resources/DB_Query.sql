CREATE TABLE tms.task (
  id BIGINT AUTO_INCREMENT NOT NULL,
   updated_by VARCHAR(255) NULL,
   version_no BIGINT NULL,
   created_by VARCHAR(255) NULL,
   created_date date NULL,
   last_updated_date date NULL,
   deleted INT NULL,
   title VARCHAR(255) NOT NULL,
   `description` VARCHAR(255) NULL,
   status VARCHAR(255) NULL,
   priority VARCHAR(255) NULL,
   due_date date NULL,
   assigned_to BIGINT NULL,
   CONSTRAINT pk_task PRIMARY KEY (id)
);

CREATE TABLE tms.user (
  id BIGINT AUTO_INCREMENT NOT NULL,
   username VARCHAR(255) NOT NULL,
   password VARCHAR(255) NOT NULL,
   email_address VARCHAR(255) NOT NULL,
   CONSTRAINT pk_user PRIMARY KEY (id)
);

CREATE TABLE tms.user_roles (
  user_id BIGINT NOT NULL,
   `role` VARCHAR(255) NULL
);

CREATE TABLE tms.task_aud (
  id BIGINT AUTO_INCREMENT NOT NULL,
   rev INT NULL,
   `description` VARCHAR(255) NULL,
   due_date date NULL,
   priority VARCHAR(255) NULL,
   status VARCHAR(255) NULL,
   title VARCHAR(255) NULL,
   created_by VARCHAR(255) NULL,
   created_date date NULL,
   deleted BIT(1) NULL,
   last_updated_date date NULL,
   updated_by VARCHAR(255) NULL,
   assigned_to BIGINT NULL,
   CONSTRAINT pk_task_aud PRIMARY KEY (id)
);

ALTER TABLE tms.task_aud ADD CONSTRAINT FK_TASK_AUD_ON_ASSIGNED_TO FOREIGN KEY (assigned_to) REFERENCES user (id);
ALTER TABLE tms.user ADD CONSTRAINT uc_user_username UNIQUE (username);

ALTER TABLE tms.user_roles ADD CONSTRAINT fk_user_roles_on_user FOREIGN KEY (user_id) REFERENCES user (id);

ALTER TABLE tms.task ADD CONSTRAINT FK_TASK_ON_ASSIGNED_TO FOREIGN KEY (assigned_to) REFERENCES user (id);



INSERT INTO tms.email_template (name, subject, body) VALUES 
('taskDueReminder', 'Task Due Reminder', 'This is a reminder that the task "{{taskTitle}}" is due on {{dueDate}}.\n\nDescription: {{taskDescription}}');

INSERT INTO tms.email_template (name, subject, body) VALUES 
('taskAssignedNotification', 'New Task Assigned', 'You have been assigned a new task "{{taskTitle}}".\n\nDescription: {{taskDescription}}\n\nDue Date: {{dueDate}}');
commit;
