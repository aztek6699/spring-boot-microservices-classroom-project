package com.example.student.service.controller;

import com.example.student.service.service.StudentService;
import com.example.student.service.model.GenericResponse;
import com.example.student.service.model.Student;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/student")
@Slf4j
public class StudentController {

    private final StudentService service;
    final Logger logger = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    public StudentController(StudentService service) {
        this.service = service;
    }

    @GetMapping("")
    public List<Student> getStudents() {
        return service.getStudents();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenericResponse> getStudentById(@PathVariable Long id) {
        return service.getStudentById(id);
    }

    @PostMapping("/register")
    public ResponseEntity<GenericResponse> registerStudent(@RequestBody Student newStudent) {
        return service.addStudent(newStudent);
    }

    @PutMapping("/update")
    public ResponseEntity<GenericResponse> updateStudent(@RequestBody Student updateStudent) {
        return service.updateStudent(updateStudent);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<GenericResponse> deleteStudent(@PathVariable Long id) {
        return service.deleteStudent(id);
    }

    @PostMapping("/getStudentsById")
    public ResponseEntity<GenericResponse> getStudentsById(@RequestBody List<Long> studentList) {
        return service.getListOfStudentById(studentList);
    }
}
