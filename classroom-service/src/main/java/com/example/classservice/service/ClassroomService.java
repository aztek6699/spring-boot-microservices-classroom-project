package com.example.classservice.service;

import com.example.classservice.model.*;
import com.example.classservice.repo.ClassroomRepo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClassroomService {

    private static Logger LOG = LoggerFactory.getLogger(ClassroomService.class);

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private StudentService studentService;

    @Autowired
    private TeacherService teacherService;

    private final ClassroomRepo repo;

    @Autowired
    public ClassroomService(ClassroomRepo repo) {
        this.repo = repo;
    }

    //@HystrixCommand(fallbackMethod = "getFallbackClassroom")
    public ResponseEntity<GenericResponse> getClassById(Long id) {

        Optional<Classroom> classroomOptional = repo.findById(id);

        if (classroomOptional.isPresent()) {
            Classroom classroom = classroomOptional.get();

            // check if classroom has teacher and students
            if (classroom.getTeacher() == null)
                return ResponseEntity.ok(new GenericResponse(false, "classroom has no teacher", 400, null));
            if (classroom.getStudents() == null)
                return ResponseEntity.ok(new GenericResponse(false, "classroom has no students", 400, null));

            GenericResponse teacherResponse = teacherService.getTeacher(classroom.getTeacher());
            GenericResponse studentResponse = studentService.getStudents(classroom.getStudents());

            // check if teacher and student have been found
            if (teacherResponse == null || teacherResponse.getRespData() == null)
                return ResponseEntity.ok(teacherResponse);
            if (studentResponse == null || studentResponse.getRespData() == null)
                return ResponseEntity.ok(studentResponse);

            // get classroom Dto and return response
            return ResponseEntity.ok(new GenericResponse(true,
                            "Classroom found",
                            200,
                            List.of(getClassroomDto(classroom.getId(),
                                    classroom.getName(),
                                    teacherResponse.getRespData().get(0),
                                    studentResponse.getRespData()))
                    )
            );
        } else {
            return ResponseEntity.ok(new GenericResponse(false, "Classroom not found", 400, null));
        }
    }

    public ResponseEntity<GenericResponse> getFallbackClassroom(Long id) {
        return ResponseEntity.ok(new GenericResponse(false, "heavy load, please try again later", 400, null));
    }

    // region helper functions
    private ClassroomDto getClassroomDto(Long id, String className, Object teacher, List<?> studentList) {

        // get ObjectMapper and register the Jackson JavaTimeModule to read Data object
        mapper.registerModule(new JavaTimeModule());

        // map the LinkedHashMap of teachers to a teacher object
        Teacher mappedTeacher = mapper.convertValue(teacher, new TypeReference<Teacher>() {
        });

        // map LinkedHashMap of students to List<Student>
        List<Student> mappedStudentList = mapper.convertValue(studentList, new TypeReference<List<Student>>() {
        });

        return new ClassroomDto(id, className, mappedTeacher, mappedStudentList);
    }

//    private ResponseEntity<GenericResponse> addClassroom(ClassroomDto classroomDto) {
//        Classroom classroom = new Classroom();
//
//        // check if teacher is null in classroom dto
//        if (classroomDto.getTeacher().getId() != null)
//            classroom.setTeacher(classroomDto.getTeacher().getId());
//        else
//            return ResponseEntity.ok(new GenericResponse(false, "Teacher must not be null", 400, null));
//
//        if (!classroomDto.getStudents().isEmpty() && classroomDto.getStudents() !=  null) {
//            for (Student i : classroomDto.getStudents()) {
//                classroom.getStudents().add(i.getId());
//            }
//        } else
//            return ResponseEntity.ok(new GenericResponse(false, "Student(s) must not be null", 400, null));
//    }

    // endregion

    //            Mono.zip(teacher, studentList)
//                    .subscribe(
//                            result -> {
//
//                                if (result.getT1().getRespData() == null) { // teacher is null
//                                    ResponseEntity.ok(result.getT1());
//                                } else if ((result.getT2().getRespData() == null)) { // students is null
//                                    ResponseEntity.ok(result.getT2());
//                                } else { // return classroom dto
//                                    ResponseEntity.ok(getClassroomDto(classroom.getName(), result.getT1().getRespData(), result.getT2().getRespData()));
//                                }
//                            }
//                    );


//    private GenericResponse getClassroomData(Long teacherId, List<Long> studentList) {
//
//        studentResponse.zipWith(teacherResponse).subscribe()
//
//        if (teacherResponse != null && teacherResponse.getSuccess() != null && teacherResponse.getSuccess() && teacherResponse.getRespData() != null) {
//
//            // get ObjectMapper and register the Jackson JavaTimeModule to read Data object
//            mapper.registerModule(new JavaTimeModule());
//
//            // map the LinkedHashMap of teachers
//            List<Teacher> teacherList = mapper.convertValue(teacherResponse.getRespData(), new TypeReference<List<Teacher>>() {
//            });
//
//            // get the teacher and return response
//            return teacherList.get(0);
//
//        } else {
//            return null;
//        }
//    }
}
