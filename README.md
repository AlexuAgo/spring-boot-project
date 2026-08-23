# Spring Boot Software Engineer API

A Spring Boot REST API for managing software engineers. This project demonstrates backend development skills including **CRUD operations**, **DTO mapping**, **validation**, **pagination**, **sorting**, **search filtering**, **Swagger**, and **global exception handling**.

---

## Features

- Create, read, update, and delete software engineer records.
- Search by name and/or tech stack with pagination and sorting.
- Input validation.
- Global exception handling.
- Swagger UI for interactive API documentation.
- H2 in-memory database for testing and PostgreSQL for production.

---

## Tech Stack

- **Java 21**
- **Spring Boot 3.5.6**
- **Spring Data JPA**
- **Spring Validation**
- **SpringDoc OpenAPI 2.7.0**
- **H2 Database** (test)
- **PostgreSQL** (runtime)
- **Maven** (build tool)
- **SLF4J / Logback** 
- **Docker / Docker Compose**
---

## Getting Started

### Prerequisites

- JDK 21
- Maven (or use IntelliJ IDEA built-in support)
- PostgreSQL 

### Running the Application

1. Clone the repository:

```
git clone https://github.com/AlexuAgo/spring-boot-project.git
```

2. Navigate to the project directory

```
cd spring-boot-project
```

3. Update application.properties with your database credentials 


4. Make sure docker is running and then:
```
docker compose up -d --build
```

5. Run the application


6. After running the app, open the follwing link to test with Swagger:
```
http://localhost:8080/swagger-ui/index.html
```

---
Author

Alexandros Agko

Email: alexandrosagko@gmail.com
Linkedin: https://www.linkedin.com/in/alexandros-agko-8a82141ba/