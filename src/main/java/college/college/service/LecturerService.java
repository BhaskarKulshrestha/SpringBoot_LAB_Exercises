package college.college.service;

import college.college.model.Lecturer;
import college.college.repository.LecturerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service // Marks this as a service class for business logic
public class LecturerService {

    @Autowired
    private LecturerRepository lecturerRepository;

    // Create a new lecturer
    public Lecturer addLecturer(Lecturer lecturer) {
        return lecturerRepository.save(lecturer);
    }

    // Retrieve all lecturers
    public List<Lecturer> getAllLecturers() {
        return lecturerRepository.findAll();
    }

    // Get a lecturer by ID
    public Optional<Lecturer> getLecturerById(Long id) {
        return lecturerRepository.findById(id);
    }

    // Update lecturer details
    public Lecturer updateLecturer(Long id, Lecturer updatedLecturer) {
        return lecturerRepository.findById(id).map(lecturer -> {
            lecturer.setLecturerName(updatedLecturer.getLecturerName());
            lecturer.setAddress(updatedLecturer.getAddress());
            lecturer.setDepartment(updatedLecturer.getDepartment());
            lecturer.setEmail(updatedLecturer.getEmail());
            lecturer.setPhone(updatedLecturer.getPhone());
            lecturer.setCourseHandled(updatedLecturer.getCourseHandled());
            return lecturerRepository.save(lecturer);
        }).orElseThrow(() -> new RuntimeException("Lecturer not found with id " + id));
    }

    // Delete lecturer
    public void deleteLecturer(Long id) {
        lecturerRepository.deleteById(id);
    }

    // Search lecturers by department
    public List<Lecturer> findLecturersByDepartment(String department) {
        return lecturerRepository.findByDepartment(department);
    }
}
