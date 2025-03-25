# 📚 College Management System - Spring Boot MVC Project

## **📖 Overview**
This is a **Spring Boot MVC** project that provides a **REST API** for managing college lecturers. The project follows the **Model-View-Controller (MVC) architecture**, integrating **Spring Data JPA** and **MySQL** as the database.

---
## **📂 Project Structure**
The project is structured into different layers:

📂 **Project Root**  
┣ 📂 `src/main/java/college`  
┃ ┣ 📂 `model` → (Holds entity classes for the database)  
┃ ┣ 📂 `repository` → (Handles database operations using JPA)  
┃ ┣ 📂 `service` → (Contains business logic)  
┃ ┣ 📂 `controller` → (Handles API requests)  
┃ ┣ 📜 `CollegeApplication.java` → (Main Spring Boot entry point)  
┣ 📂 `src/main/resources`  
┃ ┣ 📜 `application.properties` → (Database configuration)  
┣ 📂 `pom.xml` → (Manages dependencies)

---

## **⚙️ Technologies Used**
- **Java 17+**
- **Spring Boot 3.4.4**
- **Spring Data JPA (Hibernate)**
- **MySQL**
- **Lombok (for reducing boilerplate code)**
- **Postman (for API testing)**

---

## **📌 Project Flow (How It Works)**
### **Step 1: User Sends a Request**
The **user** (client) sends an HTTP request (GET, POST, DELETE) to the **Lecturer API**.

### **Step 2: Controller Handles Request**
The `LecturerController` handles the request and calls `LecturerService`.

### **Step 3: Service Layer Processes the Request**
The `LecturerService` processes the request and interacts with `LecturerRepository` to fetch or update data.

### **Step 4: Repository Layer Communicates with Database**
The `LecturerRepository` performs the necessary SQL operations using **Spring Data JPA**.

### **Step 5: Response Sent Back**
Once data is retrieved or updated, the response is sent back to the client.

---

## **🔍 Understanding Each Layer**
### **1️⃣ Model Layer (`Lecturer.java`)**
📌 **What it does?**
- Represents the **Lecturer** table in the database.
- Uses **JPA annotations** to map fields to table columns.

📌 **Code Example:**
```java
@Entity
@Table(name = "lecturers")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class Lecturer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  
    private Long lecturerId;

    @Column(nullable = false) 
    private String lecturerName;
    
    private String address;
    private String department;
    private String email;
    private String phone;
    private String courseHandled;
}
```
🔍 **How It Works?**
- `@Entity` → Marks this class as a table.
- `@Table(name = "lecturers")` → Sets the table name.
- `@Id @GeneratedValue(strategy = GenerationType.IDENTITY)` → Defines a primary key with auto-increment.

---

### **2️⃣ Repository Layer (`LecturerRepository.java`)**
📌 **What it does?**
- Communicates with the database.
- Uses **Spring Data JPA** for database operations.

📌 **Code Example:**
```java
@Repository
public interface LecturerRepository extends JpaRepository<Lecturer, Long> {
    List<Lecturer> findByDepartment(String department);
}
```
🔍 **How It Works?**
- `extends JpaRepository<Lecturer, Long>` → Provides CRUD operations.
- `findByDepartment(String department)` → Custom query method to search by department.

---

### **3️⃣ Service Layer (`LecturerService.java`)**
📌 **What it does?**
- Contains **business logic**.
- Calls `LecturerRepository` for database operations.

📌 **Code Example:**
```java
@Service
public class LecturerService {

    @Autowired
    private LecturerRepository lecturerRepository;

    public Lecturer saveLecturer(Lecturer lecturer) {
        return lecturerRepository.save(lecturer);
    }

    public List<Lecturer> getAllLecturers() {
        return lecturerRepository.findAll();
    }

    public Optional<Lecturer> getLecturerById(Long id) {
        return lecturerRepository.findById(id);
    }

    public void deleteLecturer(Long id) {
        lecturerRepository.deleteById(id);
    }

    public List<Lecturer> getLecturersByDepartment(String department) {
        return lecturerRepository.findByDepartment(department);
    }
}
```
🔍 **How It Works?**
- `@Autowired LecturerRepository` → Injects repository.
- Provides methods to **create, retrieve, delete, and search** lecturers.

---

### **4️⃣ Controller Layer (`LecturerController.java`)**
📌 **What it does?**
- Defines **REST APIs** to handle HTTP requests.

📌 **Code Example:**
```java
@RestController
@RequestMapping("/lecturers")
public class LecturerController {

    @Autowired
    private LecturerService lecturerService;

    @PostMapping("/add")
    public Lecturer createLecturer(@RequestBody Lecturer lecturer) {
        return lecturerService.saveLecturer(lecturer);
    }

    @GetMapping("/all")
    public List<Lecturer> getAllLecturers() {
        return lecturerService.getAllLecturers();
    }

    @GetMapping("/{id}")
    public Optional<Lecturer> getLecturerById(@PathVariable Long id) {
        return lecturerService.getLecturerById(id);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteLecturer(@PathVariable Long id) {
        lecturerService.deleteLecturer(id);
        return "Lecturer removed";
    }

    @GetMapping("/search/{department}")
    public List<Lecturer> getLecturersByDepartment(@PathVariable String department) {
        return lecturerService.getLecturersByDepartment(department);
    }
}
```
🔍 **How It Works?**
- `@RestController` → Defines REST APIs.
- `@RequestMapping("/lecturers")` → Base URL for API.
- Uses **GET, POST, DELETE** methods.

---

## **⚡ Running the Project**
### **Step 1: Install MySQL**
Ensure MySQL is installed and running.

Start MySQL:
```bash
brew services start mysql  # Mac
sudo systemctl start mysql  # Linux
net start mysql  # Windows
```

Create Database:
```sql
CREATE DATABASE college_db;
```

---

### **Step 2: Configure `application.properties`**
📌 **Edit `src/main/resources/application.properties`:**
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/college_db?allowPublicKeyRetrieval=true&useSSL=false
spring.datasource.username=youruser
spring.datasource.password=yoursecurepassword
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
spring.jpa.hibernate.ddl-auto=update
```

---

### **Step 3: Run the Application**
Run the application using Maven:
```bash
mvn spring-boot:run
```

---

## **🛠️ Testing the API**
Use **Postman** or **cURL** to test:

✅ **Get All Lecturers** (GET)  
`GET http://localhost:8080/lecturers/all`

✅ **Search by Department** (GET)  
`GET http://localhost:8080/lecturers/search/Computer Science`

✅ **Delete Lecturer** (DELETE)  
`DELETE http://localhost:8080/lecturers/delete/1`

---

## **📌 Conclusion**
This project follows the **Spring Boot MVC** pattern and provides a structured way to manage lecturers using **REST APIs**. It integrates **Spring Data JPA** for seamless database operations.

🎯 **Now your API is ready! 🚀**


---

## 📌 API Endpoints & Their Purpose
| **Method** | **Endpoint**                         | **Description**                 |
|-----------|-----------------------------------|-----------------------------|
| `POST`    | `/lecturers`                      | Create a new Lecturer record |
| `GET`     | `/lecturers`                      | Get all Lecturer records     |
| `GET`     | `/lecturers/{id}`                 | Get Lecturer by ID           |
| `PUT`     | `/lecturers/{id}`                 | Update Lecturer details      |
| `DELETE`  | `/lecturers/{id}`                 | Delete Lecturer by ID        |
| `GET`     | `/lecturers/search?department=CS` | Search Lecturers by Department |

---

🔄 How the Project Works (Flow)
- Client sends an HTTP Request (POST, GET, PUT, DELETE).
- Spring Boot Controller processes the request.
- Service Layer executes business logic.
- Repository Layer interacts with MySQL database.
- Database returns data to the Service Layer.
- Controller sends HTTP Response back to the Client.


URL: http://localhost:8080/lecturers

✅ Search by Department (GET):

GET http://localhost:8080/lecturers/search/Computer Science

------------------------------------------


# **Swagger and Actuator Integration in College Management System**

## **1️⃣ What is Swagger and Why We Use It?**
Swagger (Springdoc OpenAPI) is a tool that automatically generates API documentation and provides an interactive UI where users can test API endpoints. It helps in:
- ✅ Understanding the available APIs, their parameters, and responses.
- ✅ Testing API requests directly from the browser.
- ✅ Standardizing API documentation.

## **2️⃣ What is Spring Boot Actuator and Why We Use It?**
Spring Boot Actuator provides production-ready features like monitoring and application insights. It helps in:
- ✅ Checking if the application is running correctly.
- ✅ Viewing performance metrics.
- ✅ Fetching system health details.

---

# **How Swagger Works in the Project?**

### **Step 1: Adding Dependencies in `pom.xml`**
We added the following dependencies:

| Dependency | Purpose |
|------------|---------|
| `springdoc-openapi-starter-webmvc-ui` | Enables Swagger UI for API documentation. |
| `spring-boot-starter-actuator` | Enables system monitoring endpoints like `/actuator/health`. |

These dependencies allow the Spring Boot project to integrate with Swagger UI and expose monitoring data.

---

### **Step 2: Creating Swagger Configuration Class (`SwaggerConfig.java`)**

```java
package com.college.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("College Management System API")
                        .version("1.0")
                        .description("API Documentation for managing lecturers in the college system.")
                        .contact(new Contact()
                                .name("Support Team")
                                .email("support@college.com")))
                .servers(List.of(new Server().url("http://localhost:8080").description("Local Environment")));
    }
}
```

How does this configuration work?
- @Configuration: Marks this as a Spring configuration class.
  - @Bean public OpenAPI customOpenAPI(): Creates a custom OpenAPI configuration:
    - title("College Management System API"): Sets API title.
    - version("1.0"): Defines API version.
    - description("API Documentation..."): Provides a description of the API.
    - contact(new Contact().name("Support Team").email("support@college.com")): Defines API support contact.
    - servers(List.of(new Server().url("http://localhost:8080").description("Local Environment")));: Specifies that the API runs on localhost:8080.

### Step 3: Configuring Actuator in application.properties

```
# Enable all Actuator endpoints
management.endpoints.web.exposure.include=*
management.endpoint.health.show-details=always

# Application Info (Visible under /actuator/info)
info.app.name=College Management System
info.app.description=Spring Boot application for managing lecturers
info.app.version=1.0.0
```

How does this configuration work?
- management.endpoints.web.exposure.include=*: Exposes all available actuator endpoints.
- management.endpoint.health.show-details=always: Displays detailed system health information.
- info.app.name, info.app.description, info.app.version: Defines metadata available at /actuator/info.

# **How Actuator Works in the Project?**

Spring Boot Actuator exposes system monitoring endpoints. Once configured, we can access:

| **Endpoint**          | **URL**                                      | **Purpose**                                   |
|----------------------|--------------------------------|-------------------------------------------|
| **Health Check**     | `http://localhost:8080/actuator/health`  | Checks if the application is running.   |
| **Application Info** | `http://localhost:8080/actuator/info`    | Displays metadata like app name and version. |
| **Metrics**         | `http://localhost:8080/actuator/metrics`  | Provides performance insights.          |


### Testing the Integration
✅ 1. Access Swagger UI
    URL: http://localhost:8080/swagger-ui.html
    What it does?
    Displays a web interface listing all API endpoints.
    Allows users to test GET, POST, PUT, and DELETE requests.
    Shows request parameters and responses.

✅ 2. Check Actuator Endpoints:

Try accessing:

- Health Check: http://localhost:8080/actuator/health
```json
{
  "status": "UP"
}

```
Application Info: http://localhost:8080/actuator/info

```json
{
  "app": {
    "name": "College Management System",
    "description": "Spring Boot application for managing lecturers",
    "version": "1.0.0"
  }
}

```
Metrics: http://localhost:8080/actuator/metrics
- Displays application performance data.


### 💡 Summary: How the Flow Works?
- 1️⃣ A request is sent to http://localhost:8080/swagger-ui.html.
- 2️⃣ Swagger UI loads and displays all API endpoints.
- 3️⃣ The user clicks on an endpoint (e.g., GET /lecturers/{id}).
- 4️⃣ Swagger sends the request to the backend Spring Boot application.
- 5️⃣ The LecturerController handles the request and retrieves data from the LecturerService.
- 6️⃣ The LecturerService interacts with the LecturerRepository, which fetches data from the database.
- 7️⃣ The response is displayed in Swagger UI.

🚀 Actuator provides monitoring and insights into the application's health and performance!

    

