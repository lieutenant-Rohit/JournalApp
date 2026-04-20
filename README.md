# 📓 JournalApp - Secure Multi-User REST API

A production-ready, RESTful backend for a journaling application. Built with **Spring Boot** and **MongoDB**, this project demonstrates enterprise-level backend architecture, focusing on robust security, data isolation, and highly scalable database modeling.

## 🚀 Key Features & Architecture

* **Secure Authentication & Authorization:** Implemented Spring Security using Basic Auth. Passwords are cryptographically hashed in the database using **BCrypt**.
* **Strict Data Isolation:** Utilizing `SecurityContextHolder`, the API ensures multi-tenant security. Authenticated users can *only* read, write, update, or delete their own private journal entries.
* **Global Exception Handling:** Replaced generic Spring Boot error pages with a `@RestControllerAdvice` implementation. All API errors (e.g., duplicate usernames, missing users, validation failures) return standardized, clean JSON error responses.
* **Cascading Deletes:** Ensures database integrity. If a user deletes their account, the application automatically wipes all associated journal entries, preventing orphaned data in the database.
* **Scalable Data Modeling (Parent-Referencing):** Avoids the MongoDB *Unbounded Array Anti-Pattern* by storing a `userName` reference in the journal document rather than an endlessly growing array of IDs inside the User document.
* **Data Transfer Objects (DTOs):** Strict separation between internal Database Entities and external API responses. Uses Jakarta Validation to sanitize incoming requests.

## 🛠️ Tech Stack

* **Language:** Java 21
* **Framework:** Spring Boot 3.x
* **Security:** Spring Security
* **Database:** MongoDB (NoSQL)
* **Utilities:** Lombok, Jakarta Bean Validation

## 📋 API Endpoints

| HTTP Method | Endpoint | Description | Auth Required |
| :--- | :--- | :--- | :--- |
| **POST** | `/api/users` | Register a new user | ❌ No |
| **DELETE** | `/api/users` | Delete your account & all journals | 🔐 Yes |
| **POST** | `/api/journal/{userName}` | Create a new journal entry | 🔐 Yes |
| **GET** | `/api/journal/user/{userName}`| Fetch all entries for a specific user | 🔐 Yes |
| **GET** | `/api/journal/id/{id}` | Fetch a specific entry by its ID | 🔐 Yes |
| **PUT** | `/api/journal/id/{id}` | Update a specific entry | 🔐 Yes |
| **DELETE** | `/api/journal/id/{id}` | Delete a specific entry | 🔐 Yes |

*Note: All secured endpoints require Basic Authentication.*

## ⚙️ Local Setup & Installation

**1. Clone the repository:**
```bash
git clone https://github.com/lieutenant-Rohit/JournalApp.git
cd JournalApp

2. Configure MongoDB:
Ensure MongoDB is running locally on port 27017 (or update application.properties with your specific MongoDB URI).

3. Application Properties:
Ensure your src/main/resources/application.properties includes the following to enforce unique constraints on usernames:

Properties
spring.data.mongodb.uri=mongodb://localhost:27017/journaldb
spring.data.mongodb.auto-index-creation=true
4. Run the Application:

Bash
mvn spring-boot:run
The server will start on http://localhost:8080.

🚧 Future Roadmap
JWT Implementation: Migrate from Basic Authentication to stateless JSON Web Tokens for frontend compatibility.
Pagination And Sorting Implementation

