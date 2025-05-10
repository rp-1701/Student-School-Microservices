# School-Student Microservices Application

A microservices-based application that manages schools and students using Spring Cloud, with service discovery and API Gateway.

## Architecture

The application consists of four main components:

1. **Service Registry (Eureka Server)**
   - Port: 8761
   - Handles service registration and discovery
   - Dashboard available at: http://localhost:8761

2. **API Gateway**
   - Port: 8080
   - Routes requests to appropriate services
   - Entry point for all client requests

3. **School Service**
   - Default Port: 8081
   - Manages school-related operations
   - Uses H2 in-memory database

4. **Student Service**
   - Port: 8082
   - Manages student-related operations
   - Communicates with School Service
   - Uses H2 in-memory database

## Prerequisites

- Java 17
- Maven
- Spring Boot 3.x
- Spring Cloud

## Getting Started

### 1. Start the Services in Order

```bash
# 1. Start Service Registry
cd Service-Registry
mvn spring-boot:run

# 2. Start API Gateway
cd ../API-Gateway
mvn spring-boot:run

# 3. Start School Service
cd ../School-Service
mvn spring-boot:run

# 4. Start Student Service
cd ../Student-Service
mvn spring-boot:run
```

### 2. Verify Services
Open Eureka Dashboard: http://localhost:8761
You should see all services registered:
- API-GATEWAY
- SCHOOL-SERVICE
- STUDENT-SERVICE

## API Documentation

### School Service APIs

All school APIs are accessible through the gateway (port 8080)

1. Create School
```bash
POST http://localhost:8080/school/create
Content-Type: application/json

{
    "schoolName": "Sample School",
    "location": "Sample Location"
}
```

2. Get School by ID
```bash
GET http://localhost:8080/school/get/{schoolId}
```

3. Get All Schools
```bash
GET http://localhost:8080/school/getall
```

4. Update School
```bash
PUT http://localhost:8080/school/update/{id}
Content-Type: application/json

{
    "schoolName": "Updated School Name",
    "location": "Updated Location"
}
```

5. Delete School
```bash
DELETE http://localhost:8080/school/delete/{id}
```

### Student Service APIs

1. Create Student
```bash
POST http://localhost:8080/student/create
Content-Type: application/json

{
    "firstName": "John",
    "lastName": "Doe",
    "schoolId": 1
}
```

2. Get Student by ID
```bash
GET http://localhost:8080/student/get/{studentId}
```

3. Get All Students
```bash
GET http://localhost:8080/student/getall
```

## Database Access

### H2 Console Access

Both services use H2 in-memory databases accessible via web console:

- School Service H2 Console: http://localhost:8081/h2-console
  ```
  JDBC URL: jdbc:h2:mem:school
  Username: sa
  Password: [empty]
  ```

- Student Service H2 Console: http://localhost:8082/h2-console
  ```
  JDBC URL: jdbc:h2:mem:student
  Username: sa
  Password: [empty]
  ```

## Load Balancing

The application supports running multiple instances of services. To run additional instances:

```bash
# Run second instance of School Service on different port
java -jar -Dserver.port=8084 School-Service/target/School-Service-0.0.1-SNAPSHOT.jar
```

## Notes

- The H2 databases are in-memory, so data will be lost when services restart
- Each instance of a service has its own database
- Services are discovered automatically through Eureka
- All requests should go through the API Gateway (port 8080)


