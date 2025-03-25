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