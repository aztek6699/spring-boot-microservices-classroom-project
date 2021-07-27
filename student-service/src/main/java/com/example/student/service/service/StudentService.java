package com.example.student.service.service;

import com.example.student.service.model.GenericResponse;
import com.example.student.service.model.Student;
import com.example.student.service.repo.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepo repo;

    @Autowired
    public StudentService(StudentRepo repo) {
        this.repo = repo;
    }

    public List<Student> getStudents() {
        return repo.findAll();
    }

    public ResponseEntity<GenericResponse> getStudentById(@PathVariable Long id) {
        Optional<Student> student = repo.findById(id);
        if (student.isPresent()) {
            return ResponseEntity.ok(new GenericResponse(true, "Student found!", 200, List.of(student)));
        } else {
            return ResponseEntity.ok(new GenericResponse(false, "Student not found!", 400, null));
        }
    }

    public ResponseEntity<GenericResponse> addStudent(Student newStudent) {
        Optional<Student> studentOptional = repo.findStudentByEmail(newStudent.getEmail());
        if (studentOptional.isEmpty()) {
            repo.save(newStudent);
            return ResponseEntity.ok(new GenericResponse(true, newStudent.getName() + " registered!", 200, null));
        } else {
            return ResponseEntity.ok(new GenericResponse(false, newStudent.getEmail() + " already exists!", 400, null));
        }
    }

    public ResponseEntity<GenericResponse> updateStudent(Student student) {
        Optional<Student> studentOptional = repo.findById(student.getId());
        if (studentOptional.isPresent()) {
            repo.save(student);
            return ResponseEntity.ok(new GenericResponse(true, student.getName() + " updated!", 200, null));
        } else {
           return ResponseEntity.ok(new GenericResponse(false, "Cannot update student as student does not exist!", 400, null));
        }
    }

    public ResponseEntity<GenericResponse> deleteStudent(Long id) {
        Optional<Student> studentOptional = repo.findById(id);
        if (studentOptional.isPresent()) {
            repo.deleteById(id);
            return ResponseEntity.ok(new GenericResponse(true, id + " deleted!", 200, null));
        } else {
            return ResponseEntity.ok(new GenericResponse(false, "Cannot delete student as student does not exist!", 400, null));
        }
    }

    public ResponseEntity<GenericResponse> getListOfStudentById(List<Long> studentList) {
        Optional<List<Student>> optionalStudentList = repo.findByIdIn(studentList);

        if (optionalStudentList.isPresent()) {
            return ResponseEntity.ok(new GenericResponse(true, "Got student list", 200, optionalStudentList.get()));
        } else {
            return ResponseEntity.ok(new GenericResponse(false, "No students found", 400, null));
        }
    }
}
