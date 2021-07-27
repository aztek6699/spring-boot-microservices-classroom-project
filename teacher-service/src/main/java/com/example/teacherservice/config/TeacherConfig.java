package com.example.teacherservice.config;

import com.example.teacherservice.model.Teacher;
import com.example.teacherservice.repo.TeacherRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class TeacherConfig {

    @Bean
    CommandLineRunner commandLineRunner(TeacherRepo repo) {
        return args -> {
            Teacher sheraz = new Teacher(
                    "Sheraz",
                    "Android",
                    "sheraz@mail.com",
                    LocalDate.of(1980, Month.MARCH, 3)
            );

            repo.saveAll(
                    List.of(sheraz)
            );
        };
    }
}
