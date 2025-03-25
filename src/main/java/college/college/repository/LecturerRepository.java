package college.college.repository;

import college.college.model.Lecturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository // Marks this interface as a Spring Data Repository
public interface LecturerRepository extends JpaRepository<Lecturer, Long> {

    // Method to search lecturers by department
    List<Lecturer> findByDepartment(String department);
}
