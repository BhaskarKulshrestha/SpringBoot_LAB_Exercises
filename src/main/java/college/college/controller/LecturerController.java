package college.college.controller;

import college.college.model.Lecturer;
import college.college.service.LecturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController // Marks this class as a REST controller
@RequestMapping("/api/lecturers") // Base URL for this controller
public class LecturerController {

    @Autowired
    private LecturerService lecturerService;

    // Create a new lecturer
    @PostMapping
    public Lecturer createLecturer(@RequestBody Lecturer lecturer) {
        return lecturerService.addLecturer(lecturer);
    }

    // Get all lecturers
    @GetMapping
    public List<Lecturer> getAllLecturers() {
        return lecturerService.getAllLecturers();
    }

    // Get lecturer by ID
    @GetMapping("/{id}")
    public ResponseEntity<Lecturer> getLecturerById(@PathVariable Long id) {
        Optional<Lecturer> lecturer = lecturerService.getLecturerById(id);
        return lecturer.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update lecturer
    @PutMapping("/{id}")
    public ResponseEntity<Lecturer> updateLecturer(@PathVariable Long id, @RequestBody Lecturer lecturer) {
        try {
            return ResponseEntity.ok(lecturerService.updateLecturer(id, lecturer));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete lecturer
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLecturer(@PathVariable Long id) {
        lecturerService.deleteLecturer(id);
        return ResponseEntity.noContent().build();
    }

    // Search lecturers by department
    @GetMapping("/search")
    public List<Lecturer> findLecturersByDepartment(@RequestParam String department) {
        return lecturerService.findLecturersByDepartment(department);
    }
}
