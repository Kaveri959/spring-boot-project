# 🏥 Hospital Management System

A Spring Boot REST API application that allows Admins and Users to manage patients, doctors, appointments, and billing.

## ✅ Features
- 🧑‍⚕️ Add, update, delete Patients
- 🩺 Schedule Appointments
- 💳 Billing Management
- 🔐 JWT Authentication & Role-based Access
- 📘 Swagger UI for API Testing

## 🛠 Tech Stack
- Spring Boot
- Spring Security (JWT)
- Lombok, JPA, MySQL
- Swagger for API docs
- Postman / Thunder Client

## 🔐 Login Roles
| Role  | Username | Password |
|-------|----------|----------|
| Admin | `admin`  | `1234`   |
| User  | `user`   | `1234`   |

## ▶️ How to Run

```bash
# Clone the project
git clone https://github.com/Kaveri959/spring-boot-project.git

# Run Spring Boot app
./mvnw spring-boot:run
