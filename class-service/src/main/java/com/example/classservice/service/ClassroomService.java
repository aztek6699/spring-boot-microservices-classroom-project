package com.example.classservice.service;

import com.example.classservice.model.*;
import com.example.classservice.repo.ClassroomRepo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;

@Service
public class ClassroomService {

    private static Logger LOG = LoggerFactory.getLogger(ClassroomService.class);

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private ObjectMapper mapper;

    private final ClassroomRepo repo;

    @Value("${teacher.service.url}")
    private String teacherBaseURL;
    @Value("${student.service.url}")
    private String studentBaseURL;

    @Autowired
    public ClassroomService(ClassroomRepo repo) {
        this.repo = repo;
    }

    public ResponseEntity<GenericResponse> getClassById(Long id) {

        Optional<Classroom> classroomOptional = repo.findById(id);

        if (classroomOptional.isPresent()) {
            Classroom classroom = classroomOptional.get();

            // check if classroom has teacher and students
            if (classroom.getTeacher() == null)
                return ResponseEntity.ok(new GenericResponse(false, "classroom has no teacher", 400, null));
            if (classroom.getStudents() == null)
                return ResponseEntity.ok(new GenericResponse(false, "classroom has no students", 400, null));

            GenericResponse teacherResponse = getTeacher(classroom.getTeacher());
            GenericResponse studentResponse = getStudents(classroom.getStudents());

            // check if teacher and student have been found
            if (teacherResponse == null || teacherResponse.getRespData() == null)
                return ResponseEntity.ok(teacherResponse);
            if (studentResponse == null || studentResponse.getRespData() == null)
                return ResponseEntity.ok(studentResponse);

            // get classroom Dto and return response
            return ResponseEntity.ok(new GenericResponse(true,
                    "Classroom found",
                    200,
                    List.of(getClassroomDto(classroom.getId(), classroom.getName(), teacherResponse.getRespData().get(0), studentResponse.getRespData()))));

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

        } else {
            return ResponseEntity.ok(new GenericResponse(false, "Classroom not found", 400, null));
        }
    }

    // region helper functions
    private GenericResponse getTeacher(Long teacherId) {
        String teacherUrl = teacherBaseURL + "" + teacherId;
        return webClientBuilder.build()
                .get()
                .uri(teacherUrl)
                .retrieve()
                .bodyToMono(GenericResponse.class)
                .block();
    }

    private GenericResponse getStudents(List<Long> list) {
        String studentUrl = studentBaseURL + "getStudentsById";
        return webClientBuilder.build()
                .post()
                .uri(studentUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(list.toString())
                .retrieve()
                .bodyToMono(GenericResponse.class)
                .block();
    }

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

    // endregion

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
