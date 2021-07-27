package com.example.teacherservice.repo;

import com.example.teacherservice.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeacherRepo extends JpaRepository<Teacher, Long> {

    Optional<Teacher> findTeacherByEmail(String email);

    Optional<Teacher> findTeacherBySubject(String subject);
}
