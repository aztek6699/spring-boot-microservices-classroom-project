package com.example.student.service.repo;

import com.example.student.service.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepo extends JpaRepository<Student, Long> {

    Optional<Student> findStudentByEmail(String email);

    Optional<List<Student>> findByIdIn(List<Long> idList);
}
