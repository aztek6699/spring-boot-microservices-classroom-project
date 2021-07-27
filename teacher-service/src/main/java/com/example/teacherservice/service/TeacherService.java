package com.example.teacherservice.service;

import com.example.teacherservice.model.GenericResponse;
import com.example.teacherservice.model.Teacher;
import com.example.teacherservice.repo.TeacherRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {

    private final TeacherRepo repo;

    @Autowired
    public TeacherService(TeacherRepo repo) {
        this.repo = repo;
    }

    public List<Teacher> getTeachers() {
        return repo.findAll();
    }

    public ResponseEntity<GenericResponse> getTeacherById(Long id) {
        Optional<?> teacher = repo.findById(id);
        if (teacher.isPresent()) {
            return ResponseEntity.ok(new GenericResponse(true, "Teacher Found!", 200, List.of(teacher)));
        } else
            return ResponseEntity.ok(new GenericResponse(false, "Teacher not found!", 400, null));
    }

    public ResponseEntity<GenericResponse> updateTeacher(Teacher teacher) {
        Optional<Teacher> teacherOptional = repo.findById(teacher.getId());
        if (teacherOptional.isPresent()) {
            return ResponseEntity.ok(new GenericResponse(true, "Teacher updated!", 200, null));
        } else {
            return ResponseEntity.ok(new GenericResponse(false, "Teacher does not exist, cannot update!", 400, null));
        }
    }

    public ResponseEntity<GenericResponse> addTeacher(Teacher teacher) {
        Optional<Teacher> teacherOptional = repo.findTeacherByEmail(teacher.getEmail());

        if (teacherOptional.isPresent()) {
            return ResponseEntity.ok(new GenericResponse(false, "Teacher already exists!", 400, null));
        } else {
            return ResponseEntity.ok(new GenericResponse(true, "Teacher added!", 200, null));
        }
    }

    public ResponseEntity<GenericResponse> deleteTeacher(Long id) {
        Optional<Teacher> teacherOptional = repo.findById(id);

        if (teacherOptional.isPresent()) {
            repo.deleteById(id);
            return ResponseEntity.ok(new GenericResponse(true, "Teacher deleted!", 200, null));
        } else {
            return ResponseEntity.ok(new GenericResponse(false, "teacher does not exist!", 400, null));
        }
    }

    public ResponseEntity<GenericResponse> getTeacherBySubject(String subject) {
        Optional<Teacher> teacherOptional = repo.findTeacherBySubject(subject);

        if (teacherOptional.isPresent()) {
            return ResponseEntity.ok(new GenericResponse(true, "Teacher Available", 200, List.of(teacherOptional)));
        } else {
            return ResponseEntity.ok(new GenericResponse(false, "No teacher with subject", 400, null));
        }
    }
}
