# Task Management System

This project is a Task Management System that includes functionalities for task creation, assignment, searching, auditing, and notifications. The system is built using Spring Boot and employs various Spring Security features for authentication and authorization.

## Table of Contents
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
- [Project Structure](#project-structure)
- [API Endpoints](#api-endpoints)
- [Audit Logging](#audit-logging)
- [Pagination and Search](#pagination-and-search)
- [Email Notifications](#email-notifications)
- [Md4PasswordEncoder](#Md4PasswordEncoder)


## Features
- User Authentication with JWT
- Role-based Authorization
- Task Creation and Management
- Task Assignment to Users
- Email Notifications for Task Due Dates and Assignments
- Audit Logging using Hibernate Envers
- Pagination and Search for Tasks

## Technologies Used
- Spring Boot
- Spring Security
- Spring Data JPA
- Hibernate Envers
- JavaMailSender
- MySQL
- Lombok
- Maven

## Getting Started
1. **Clone the repository:**
   ```sh
   git clone https://github.com/your-repo/task-management-system.git
   cd task-management-system

## Project Structure

```sh
src
├── main
│   ├── java
│   │   └── com
│   │       └── banq
│   │           └── assign
│   │               ├── common             # Common classes and utilities
│   │               ├── notification       # Notification related classes
│   │               └── task               # Task related classes
|   |               └── user               # User related classes
│
│   ├── resources
│   │   ├── application.properties         # Application configuration
└── test                                   # Test classes
```

## API Endpoints

   - [Get All Tasks](#get-all-tasks)
   - [Get Task by ID](#get-task-by-id)
   - [Create Task](#create-task)
   - [Update Task](#update-task)
   - [Delete Task](#delete-task)
   - [Get Task History](#get-task-history)
   - [Search Tasks](#search-tasks)

### Get Task by ID
**Endpoint:** `GET /api/tasks/{id}`

**Description:** Retrieves a task by its ID.

**Path Variables:**
- `id` (Long): The ID of the task to retrieve.

**Response:**
- `200 OK`: Returns the `TaskDTO` object.
- `404 Not Found`: If the task with the given ID does not exist.

### Create Task
**Endpoint:** `POST /api/tasks`

**Description:** Creates a new task.

**Request Body:**
- `CreateTaskDTO`: The details of the task to create.

**Response:**
- `201 Created`: Returns the created `TaskDTO` object.
- `400 Bad Request`: If the request body is invalid.

### Update Task
**Endpoint:** `PUT /api/tasks`

**Description:** Updates an existing task.

**Request Body:**
- `TaskDTO`: The updated details of the task.

**Response:**
- `200 OK`: Returns the updated `TaskDTO` object.
- `404 Not Found`: If the task with the given ID does not exist.
- `400 Bad Request`: If the request body is invalid.

### Delete Task
**Endpoint:** `DELETE /api/tasks/{id}`

**Description:** Deletes a task by its ID.

**Path Variables:**
- `id` (Long): The ID of the task to delete.

**Response:**
- `204 No Content`: If the task was successfully deleted.
- `404 Not Found`: If the task with the given ID does not exist.

### Get Task History
**Endpoint:** `GET /api/tasks/history/{id}`

**Description:** Retrieves the history of a task by its ID.

**Path Variables:**
- `id` (Long): The ID of the task to retrieve history for.

**Response:**
- `200 OK`: Returns a list of `TaskAudDTO` objects.
- `404 Not Found`: If the task with the given ID does not exist.

### Search Tasks
**Endpoint:** `POST /api/tasks/search?page=0&size=30`

**Description:** Searches for tasks based on criteria and supports pagination.

**Request Body:**
- `TaskDTO`: The search criteria.

**Request Parameters:**
- `page` (int): The page number to retrieve.
- `size` (int): The number of records per page.

**Response:**
- `200 OK`: Returns a `TaskSearchResponseDTO` object with search results and pagination details.

## Data Transfer Objects (DTOs)

### TaskDTO
```java
@Data
public class TaskDTO {
    private Long id;
    private String title;
    private String description;
    private Status status;
    private String priority;
    private LocalDate dueDate;
    private String assignedToName;
}
```

## Audit Logging

### Overview

The system uses JPA Auditing to maintain audit logs for entities. This enables tracking of changes, such as who created, updated, or deleted an entity, and when these changes occurred.

### Configuration

The audit configuration is managed by the `AuditorConfig` class. It enables JPA auditing and sets up the `AuditorAware` bean which provides the current auditor (user) for the audit logs.

**Key Points:**
- **AuditorAware Bean:** This bean is used by JPA to get the username of the current auditor from the security context. If no security context is available, it defaults to "SYSTEM".

### How It Works

1. **Entity Configuration:** Entities are annotated with audit-related annotations (`@CreatedBy`, `@LastModifiedBy`, etc.) to automatically capture who created or modified an entity and when.
2. **Auditing Data:** When an entity is created, updated, or deleted, the audit fields are automatically populated with the current user's information or the default "SYSTEM" if no user is authenticated.
3. **History Tracking:** For entities that require historical data, Hibernate Envers is used. It maintains a history of all changes made to the entities, allowing you to query historical data.

### Example of Audited Fields

- `createdBy`: Stores the username of the person who created the entity.
- `lastModifiedBy`: Stores the username of the person who last modified the entity.
- `createdDate`: The timestamp when the entity was created.
- `lastModifiedDate`: The timestamp when the entity was last modified.

By setting up auditing in your application, you ensure that all significant changes to your data are logged and can be reviewed as needed.


## Pagination and Search

### Overview

The Pagination and Search functionality in this project allows users to search for tasks with pagination support, ensuring efficient handling of large data sets and improved user experience.

### API Endpoint

- **Endpoint:** `POST /api/tasks/search`
- **Description:** Searches for tasks based on the provided search criteria and returns a paginated result.

### Request

- **Content-Type:** `application/json`
- **Request Body:** A JSON object containing search criteria for tasks.

### Response

- **Status Code:** `200 OK`
- **Response Body:** A JSON object containing the paginated search results.

### Response Object

The response object includes the following fields:

- **`tasks`**: A list of `TaskDTO` objects representing the search results. Each `TaskDTO` includes details about the task such as ID, title, description, status, priority, due date, and assigned user's name.
- **`totalPages`**: The total number of pages available based on the search results.
- **`totalElements`**: The total number of tasks matching the search criteria.
- **`size`**: The number of tasks returned per page.
- **`first`**: A boolean indicating whether the current page is the first page.
- **`last`**: A boolean indicating whether the current page is the last page.
- **`numberOfElements`**: The number of tasks present in the current page.

### Usage

1. **Search Tasks:** Send a `POST` request to `/api/tasks/search` with a JSON payload containing search criteria. The criteria can include task attributes such as title, description, status, priority, etc.

2. **Paginate Results:** The response will include pagination details, allowing users to navigate through pages of results efficiently.

This functionality helps in efficiently handling large volumes of tasks and provides users with the flexibility to search and navigate through task data seamlessly.

## Email Notifications

The Task Management System includes functionality to send email notifications for task assignments and tasks due soon. These notifications are sent using the JavaMail API library.

### Task Due Notification

**Description:** Sends an email notification when a task is due within the next day.

### Task Assignment Notification

**Description:** Sends an email notification when a task is assigned to a user.

## Md4PasswordEncoder

Md4PasswordEncoder is a password encoder that uses the MD4 hashing algorithm to store password. It provides data in rest security feature.


