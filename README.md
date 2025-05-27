# Student Course Registration System

A secure Spring Boot-based RESTful microservice that enables students to register for courses and allows admins to manage users and course data.

---

## Setup & Run Instructions

###  Requirements:
- Java 17+
- Maven
- Docker (optional for containerization)

###  Running Locally

```bash
# Clone the repository or unzip the folder
cd assesment

# Build the project
./mvnw clean install

# Run the application
./mvnw spring-boot:run
```

The app will be available at: `http://localhost:8080`

### 🐳 Run with Docker

```bash
# Build Docker image
docker build -t student-registration-app .

# Run Docker container
docker run -p 8080:8080 student-registration-app
```

---

## 🔐 Authentication (JWT)

### 📝 Register a User
**POST** `/api/auth/register`
```json
{
  "username": "alice123",
  "password": "Str0ngP@ssw0rd!",
  "role": "STUDENT"
}
```
**Response**
```json
{
  "success": true,
  "message": "Student registered successfully",
  "data": null
}
```

### 🔐 Login
**POST** `/api/auth/login`
```json
{
  "username": "alice123",
  "password": "Str0ngP@ssw0rd!"
}
```
**Response**
```json
{
  "token": "<JWT_TOKEN>",
  "type": "Bearer",
  "username": "alice123",
  "role": "STUDENT"
}
```
Use the token in the `Authorization` header:
```
Authorization: Bearer <JWT_TOKEN>
```

---

##  API Endpoints

###  View Courses
**GET** `/api/courses`
**Accessible by**: STUDENT, ADMIN
```json
[
  {
    "id": 1,
    "name": "Math",
    "instructor": "Nikshith",
    "capacity": 50,
    "currentEnrollmentCount": 0
  }
]
```

###  Add Course (Admin Only)
**POST** `/api/courses`
```json
{
  "name": "Math",
  "instructor": "Nikshith",
  "capacity": "50",
  "enrollments": []
}
```
**Response**
```json
{
  "id": 1,
  "name": "Math",
  "instructor": "Nikshith",
  "capacity": 50,
  "currentEnrollmentCount": 0
}
```

###  Enroll in Course (Student Only)
**POST** `/api/courses/enroll/{courseId}`
**Response**
```json
{
  "success": true,
  "message": "Successfully enrolled in course",
  "data": {
    "id": 1,
    "student": { "id": 3 },
    "course": {
      "id": 1,
      "name": "Math",
      "instructor": "Nikshith",
      "capacity": 50,
      "currentEnrollmentCount": 0
    },
    "enrollmentDate": "2025-05-28T03:04:03.9840805"
  }
}
```

---

##  Sample Test Users

### Admin User:
```json
{
  "username": "diana_admin",
  "password": "D!anaAdmin789",
  "role": "ADMIN"
}
```

### Student User:
```json
{
  "username": "alice123",
  "password": "Str0ngP@ssw0rd!",
  "role": "STUDENT"
}
```

---

##  Notes
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- JWT expires in default configured duration (e.g., 1 hour)
- Ensure password meets complexity: uppercase, lowercase, digit, special char

---

##  Technologies Used
- Java 17, Spring Boot 3.5.0
- Spring Security, JWT, Spring Data JPA
- PostgreSQL, Docker, Swagger
- Lombok

---

## 📂 Directory Structure
```
src
├── main
│   ├── java
│   └── resources
└── test
```

---
