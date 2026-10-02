# CodeCraftHub — Learning Management Platform

**CodeCraftHub** is a simple personalized learning management platform that helps developers track the courses they want to learn.

The project consists of:

* A **Java Spring Boot REST API** backend
* A **JSON file** for data persistence instead of a database
* A **vanilla HTML/CSS/JavaScript frontend**
* A responsive learning dashboard for managing courses
* CRUD operations through REST APIs

The frontend was created and deployed using **Bolt.new**.

### Live Frontend

**CodeCraftHub Learning Dashboard:**

https://learning-management-1mky.bolt.host/

Screenshot of front end

<img width="1365" height="671" alt="2" src="https://github.com/user-attachments/assets/69a01932-def6-4420-8cb9-a1c276d76a7b" />

---

<img width="1365" height="689" alt="1" src="https://github.com/user-attachments/assets/cc6df60c-a522-4583-9751-ef8fb9acaf71" />


---

<img width="1354" height="666" alt="3" src="https://github.com/user-attachments/assets/0e32e7bb-1d87-40e5-9b69-97841a2f04b4" />


---

# Project Overview

CodeCraftHub allows a developer to maintain a personal list of courses and track their learning progress.

For every course, the platform stores:

* Course ID
* Course name
* Course description
* Target completion date
* Current learning status
* Course creation timestamp

The application supports the complete course lifecycle:

```text
Create → View → Update → Delete
```

---

# Features

## Learning Dashboard

The frontend provides a clean dashboard for managing courses.

Features include:

* View all courses
* Add new courses
* Edit existing courses
* Remove courses
* Track course status
* Set target completion dates
* Display course creation dates
* Loading indicators
* Success messages
* Error messages
* Client-side form validation
* Responsive design for desktop and mobile

---

# Course Status

Each course can have one of the following statuses:

```text
Not Started
In Progress
Completed
```

The status values must match the backend API exactly.

---

# Technology Stack

## Backend

| Technology  | Purpose                      |
| ----------- | ---------------------------- |
| Java 21     | Backend programming language |
| Spring Boot | REST API framework           |
| Spring Web  | REST endpoints               |
| Jackson     | JSON processing              |
| Maven       | Dependency management        |
| JSON file   | Data persistence             |

## Frontend

| Technology         | Purpose                        |
| ------------------ | ------------------------------ |
| HTML5              | Page structure                 |
| CSS3               | Styling and responsive layout  |
| Vanilla JavaScript | API communication and UI logic |
| Fetch API          | REST API calls                 |
| Bolt.new           | Frontend generation/deployment |

No frontend framework such as React, Angular, or Vue is used.

No database is required.

---

# Architecture

The project follows a simple full-stack architecture:

```text
                         CodeCraftHub
                              |
              ┌───────────────┴───────────────┐
              |                               |
              ▼                               ▼
       Frontend Dashboard              Spring Boot Backend
       HTML/CSS/JavaScript                  REST API
              |                               |
              | HTTP/JSON                     |
              └───────────────►───────────────┘
                                              |
                                              ▼
                                      CourseService
                                              |
                                              ▼
                                        Jackson
                                              |
                                              ▼
                                      courses.json
```

The frontend communicates with the backend using REST APIs.

---

# Live Application

The frontend is available at:

https://learning-management-1mky.bolt.host/

The dashboard provides the user interface for interacting with the course APIs.

> **Important:** The frontend URL and backend API URL are separate. The frontend must be configured to point to a running Spring Boot backend.

---

# Backend API

The frontend communicates with the following API:

```text
[http://localhost:8080]/api/courses
```

Replace:

```text
[http://localhost:8080]
```

with the URL where the Spring Boot backend is running.

For local development:

```text
http://localhost:8080/api/courses
```

---

# API Endpoints

| Method | Endpoint            | Description           |
| ------ | ------------------- | --------------------- |
| GET    | `/api/courses`      | Get all courses       |
| GET    | `/api/courses/{id}` | Get a specific course |
| POST   | `/api/courses`      | Create a new course   |
| PUT    | `/api/courses/{id}` | Update a course       |
| DELETE | `/api/courses/{id}` | Delete a course       |

---

# Course Object

A course returned by the backend has the following structure:

```json
{
  "id": 1,
  "name": "Spring Boot REST API",
  "description": "Learn REST API development using Spring Boot",
  "target_date": "2026-10-20",
  "status": "In Progress",
  "created_at": "2026-10-02T14:45:20"
}
```

### Fields

| Field         | Description                                |
| ------------- | ------------------------------------------ |
| `id`          | Automatically generated by the backend     |
| `name`        | Course name                                |
| `description` | Course description                         |
| `target_date` | Target completion date                     |
| `status`      | Current learning status                    |
| `created_at`  | Automatically generated creation timestamp |

---

# API Examples

## 1. Get All Courses

```http
GET /api/courses
```

Example:

```text
http://localhost:8080/api/courses
```

Example response:

```json
[
  {
    "id": 1,
    "name": "Spring Boot REST API",
    "description": "Learn REST API development",
    "target_date": "2026-10-20",
    "status": "In Progress",
    "created_at": "2026-10-02T14:45:20"
  }
]
```

---

# 2. Create a Course

```http
POST /api/courses
```

Request:

```json
{
  "name": "Docker",
  "description": "Learn Docker fundamentals",
  "target_date": "2026-11-01",
  "status": "Not Started"
}
```

The backend automatically generates:

```text
id
created_at
```

Example response:

```json
{
  "id": 2,
  "name": "Docker",
  "description": "Learn Docker fundamentals",
  "target_date": "2026-11-01",
  "status": "Not Started",
  "created_at": "2026-10-02T15:10:00"
}
```

---

# 3. Update a Course

```http
PUT /api/courses/{id}
```

Example:

```text
PUT /api/courses/1
```

Request:

```json
{
  "name": "Spring Boot REST API",
  "description": "Learn Spring Boot REST APIs and Postman",
  "target_date": "2026-10-25",
  "status": "Completed"
}
```

The frontend sends this request when the user edits a course.

---

# 4. Delete a Course

```http
DELETE /api/courses/{id}
```

Example:

```text
DELETE /api/courses/1
```

Successful response:

```text
204 No Content
```

---

# Frontend Functionality

The CodeCraftHub dashboard provides the following workflow.

## Add Course

The user fills out:

```text
Course Name
Description
Target Date
Status
```

The frontend validates the required fields and sends:

```http
POST /api/courses
```

After a successful response, the course list is refreshed.

---

## View Courses

When the dashboard loads, JavaScript calls:

```http
GET /api/courses
```

The returned JSON is displayed in the course list.

---

## Edit Course

When the user selects **Edit**, the course information is loaded into the editing interface.

After the user saves the changes:

```http
PUT /api/courses/{id}
```

is sent to the backend.

---

## Remove Course

When the user selects **Remove**, the frontend sends:

```http
DELETE /api/courses/{id}
```

After successful deletion, the course list is refreshed.

---

# Loading States

The frontend displays loading indicators while API operations are being performed.

Examples:

```text
Loading courses...
Saving course...
Updating course...
Deleting course...
```

This prevents the user from assuming that the application is frozen while waiting for the backend.

---

# Error Handling

The frontend handles API failures and displays user-friendly messages.

Examples include:

```text
Unable to load courses.
Failed to create course.
Unable to update course.
Failed to delete course.
```

The frontend also validates required fields before making API requests.

---

# Validation

The following fields are required:

```text
name
description
target_date
status
```

The target date must use:

```text
YYYY-MM-DD
```

Example:

```text
2026-10-20
```

Status must be one of:

```text
Not Started
In Progress
Completed
```

---

# JSON File Persistence

The backend does not use MySQL, PostgreSQL, MongoDB, or another database.

Instead, course data is stored in:

```text
courses.json
```

Example:

```json
[
  {
    "id": 1,
    "name": "Spring Boot",
    "description": "Learn Spring Boot",
    "target_date": "2026-10-20",
    "status": "In Progress",
    "created_at": "2026-10-02T14:45:20"
  }
]
```

This makes the project easy to understand and suitable for learning REST API fundamentals.

---

# Running the Backend Locally

## Prerequisites

Install:

* Java 21
* Maven
* Git

Verify Java:

```bash
java -version
```

Verify Maven:

```bash
mvn -version
```

---

## Start the Backend

Navigate to the Spring Boot project:

```bash
cd CodeCraftHub
```

Run:

```bash
mvn spring-boot:run
```

The backend will normally start at:

```text
http://localhost:8080
```

The API is available at:

```text
http://localhost:8080/api/courses
```

---

# Connecting the Frontend to the Backend

The frontend needs the backend API URL.

For local development, the API base URL should be:

```text
http://localhost:8080/api/courses
```

For a deployed backend, use the deployed backend URL:

```text
https://your-backend-domain.com/api/courses
```

The frontend then communicates with the backend using the Fetch API.

---

# CORS

If the frontend and backend are hosted on different domains, the Spring Boot backend must allow requests from the frontend origin.

For example, the frontend is hosted at:

```text
https://learning-management-1mky.bolt.host
```

while the backend might be hosted at:

```text
https://your-backend-domain.com
```

In this situation, configure CORS in the Spring Boot application to allow the frontend origin.

For local development, you may also need to allow:

```text
http://localhost:3000
```

or whichever local frontend URL you use.

---

# Troubleshooting

## Frontend shows "Failed to fetch"

Possible causes:

1. Spring Boot backend is not running.
2. The frontend API URL is incorrect.
3. CORS is not configured.
4. The backend is running on a different port.
5. The deployed backend is unavailable.

First test the backend directly:

```text
http://localhost:8080/api/courses
```

If the backend is working, it should return a JSON array.

---

## Backend returns 404

Check that the endpoint is:

```text
/api/courses
```

For example:

```text
http://localhost:8080/api/courses
```

Make sure you are not accidentally using:

```text
/api/course
```

or:

```text
/courses
```

---

## Course creation fails

Check that all required fields are provided:

```json
{
  "name": "Docker",
  "description": "Learn Docker",
  "target_date": "2026-11-01",
  "status": "Not Started"
}
```

---

## Invalid status

Only these values are accepted:

```text
Not Started
In Progress
Completed
```

For example:

```json
"status": "In Progress"
```

is valid.

---

## Invalid target date

Use:

```text
YYYY-MM-DD
```

Example:

```text
2026-10-20
```

Do not use:

```text
20/10/2026
```

or:

```text
10-20-2026
```

---

# Project Goals

CodeCraftHub is primarily a learning project focused on understanding how a frontend communicates with a backend REST API.

The main concepts demonstrated are:

### Frontend

```text
HTML
CSS
JavaScript
Fetch API
JSON
Form validation
Loading states
Error handling
```

### Backend

```text
Java
Spring Boot
REST API
Controllers
Services
Jackson
JSON file persistence
CRUD
HTTP status codes
Exception handling
```

---

# Learning Flow

The application demonstrates the following full-stack flow:

```text
User
 │
 ▼
CodeCraftHub Dashboard
 │
 │ JavaScript Fetch API
 ▼
Spring Boot REST API
 │
 ▼
CourseController
 │
 ▼
CourseService
 │
 ▼
Jackson ObjectMapper
 │
 ▼
courses.json
```

For example, when adding a course:

```text
User fills form
      ↓
JavaScript validates form
      ↓
POST /api/courses
      ↓
Spring Boot Controller
      ↓
CourseService
      ↓
courses.json updated
      ↓
JSON response
      ↓
Frontend refreshes course list
```

---

# Future Enhancements

Possible future improvements include:

* User authentication
* Multiple users
* Course categories
* Search courses
* Filter by status
* Sort by target date
* Course progress percentage
* Learning notes
* Course URLs
* Reminder notifications
* Dashboard statistics
* Database persistence
* Spring Data JPA
* PostgreSQL/MySQL
* Swagger/OpenAPI documentation
* Automated tests
* Docker deployment
* CI/CD pipeline

---

# Project Status

**Current version:** Learning Management Dashboard with REST API integration

**Frontend:** Deployed using Bolt.new

**Backend:** Java Spring Boot REST API

**Persistence:** `courses.json`

**Database:** Not used

**Authentication:** Not implemented

---

# Live Demo

**CodeCraftHub Learning Management Dashboard**

https://learning-management-1mky.bolt.host/

---

## Author

**Vrushali Phaltane**

Java Backend Developer | Spring Boot | REST APIs | AI/ML

GitHub:https://github.com/vrushaliphaltane11-ctrl/CodeCraftHub

`https://github.com/vrushaliphaltane11-ctrl`
