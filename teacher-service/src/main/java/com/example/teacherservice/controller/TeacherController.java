package com.example.teacherservice.controller;

import com.example.teacherservice.model.GenericResponse;
import com.example.teacherservice.model.Teacher;
import com.example.teacherservice.service.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/teacher")
@Slf4j
public class TeacherController {

    private final TeacherService service;

    @Autowired
    public TeacherController(TeacherService service) {
        this.service = service;
    }

    @GetMapping("")
    public List<Teacher> getTeachers() {
        return service.getTeachers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenericResponse> getTeacherById(@PathVariable Long id) {
        return service.getTeacherById(id);
    }

    @PostMapping("/register")
    public ResponseEntity<GenericResponse> addTeacher(@RequestBody Teacher newTeacher) {
        return service.addTeacher(newTeacher);
    }

    @PutMapping("/update")
    public ResponseEntity<GenericResponse> updateTeacher(@RequestBody Teacher updateTeacher) {
        return service.updateTeacher(updateTeacher);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<GenericResponse> deleteTeacher(@PathVariable Long id) {
        return service.deleteTeacher(id);
    }

    @GetMapping("/teacherBySubject/{subject}")
    public ResponseEntity<GenericResponse> getTeacherBySubject(@PathVariable String subject) {
        return service.getTeacherBySubject(subject);
    }
}

