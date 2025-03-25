package college.college.model;

import jakarta.persistence.*;
import lombok.*;

@Entity // Marks this class as a database entity
@Table(name = "lecturers") // Maps to the "lecturers" table in DB
@Data // Generates Getters, Setters, toString, hashCode, and equals
@NoArgsConstructor // Generates a default constructor
@AllArgsConstructor // Generates a constructor with all fields
public class Lecturer {

    @Id // Marks this as a primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increments ID
    private Long lecturerId;

    @Column(nullable = false)
    private String lecturerName;

    private String address;
    private String department;

    @Column(unique = true, nullable = false)
    private String email;

    private String phone;
    private String courseHandled;
}
