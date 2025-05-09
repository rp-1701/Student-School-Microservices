# School Management System Microservices

This project consists of two microservices that work together to manage schools and students:

## Architecture Overview

- **School Service** (Port: 8081)
  - Manages school information
  - Handles CRUD operations for schools
  - Provides school data to Student Service

- **Student Service** (Port: 8082)
  - Manages student information
  - Handles CRUD operations for students
  - Communicates with School Service to validate and fetch school details

## Services Details

### School Service

**Endpoints:**
```http
POST   /school/create          - Create a new school
GET    /school/get/{id}       - Get school by ID
GET    /school/getall         - Get all schools
PUT    /school/update/{id}    - Update school
DELETE /school/delete/{id}    - Delete school
```

**Data Model:**
```json
{
    "id": 1,
    "schoolName": "ABC School",
    "location": "New York"
}
```

### Student Service

**Endpoints:**
```http
POST   /student/create          - Create a new student
GET    /student/get/{id}       - Get student by ID
GET    /student/getall         - Get all students
PUT    /student/update/{id}    - Update student
DELETE /student/delete/{id}    - Delete student
```

**Data Model:**
```json
{
    "id": 1,
    "firstName": "John",
    "lastName": "Doe",
    "schoolId": 1
}
```

## Technology Stack

- Java 17
- Spring Boot
- H2 Database
- Maven
- REST APIs
- Spring Web
- JPA/Hibernate

## Setup and Running

1. **Start School Service:**
```bash
cd School-Service
mvn spring-boot:run
```
Service will start on port 8081

2. **Start Student Service:**
```bash
cd Student-Service
mvn spring-boot:run
```
Service will start on port 8082

## Database Access

Both services use H2 in-memory database:

- School Service H2 Console: http://localhost:8081/h2-console
- Student Service H2 Console: http://localhost:8082/h2-console

Database Configuration:
- JDBC URL: jdbc:h2:mem:school (for School Service)
- JDBC URL: jdbc:h2:mem:student (for Student Service)
- Username: sa
- Password: [empty]