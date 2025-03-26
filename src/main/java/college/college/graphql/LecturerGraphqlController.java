package college.college.graphql;

import college.college.model.Lecturer;
import college.college.service.LecturerService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import java.util.List;
import java.util.Optional;

@Controller
public class LecturerGraphqlController {
    private final LecturerService lecturerService;

    public LecturerGraphqlController(LecturerService lecturerService) {
        this.lecturerService = lecturerService;
    }

    @QueryMapping
    public List<Lecturer> getAllLecturers() {
        return lecturerService.getAllLecturers();
    }

    @QueryMapping
    public Optional<Lecturer> getLecturerById(@Argument Long id) {
        return lecturerService.getLecturerById(id);
    }

    @MutationMapping
    public Lecturer createLecturer(@Argument String lecturerName, @Argument String address,
                                   @Argument String department, @Argument String email,
                                   @Argument String phone, @Argument String courseHandled) {
        Lecturer lecturer = new Lecturer(null, lecturerName, address, department, email, phone, courseHandled);
        return lecturerService.addLecturer(lecturer);
    }


    @MutationMapping
    public Lecturer updateLecturer(@Argument Long id, @Argument String lecturerName, @Argument String address,
                                   @Argument String department, @Argument String email,
                                   @Argument String phone, @Argument String courseHandled) {
        Lecturer lecturer = new Lecturer(id, lecturerName, address, department, email, phone, courseHandled);
        return lecturerService.updateLecturer(id, lecturer);
    }

    @MutationMapping
    public String deleteLecturer(@Argument Long id) {
        lecturerService.deleteLecturer(id);
        return "Lecturer deleted successfully!";
    }
}
