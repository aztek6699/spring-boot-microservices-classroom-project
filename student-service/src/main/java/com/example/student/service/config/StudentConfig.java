package com.example.student.service.config;

import com.example.student.service.repo.StudentRepo;
import com.example.student.service.model.Student;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepo repo) {
        return args -> {
            Student zain = new Student(
                    "Zain",
                    "zain@mail.com",
                    LocalDate.of(1994, Month.FEBRUARY, 19)
            );

            Student khizr = new Student(
                    "Khizr",
                    "Khizr@mail.com",
                    LocalDate.of(1997, Month.JULY, 8)
            );

            repo.saveAll(
                    List.of(zain, khizr)
            );
        };
    }
}
