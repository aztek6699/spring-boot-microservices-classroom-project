package com.example.classservice.config;

import com.example.classservice.model.Classroom;
import com.example.classservice.repo.ClassroomRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ClassroomConfig {
    @Bean
    CommandLineRunner commandLineRunner(ClassroomRepo repo) {
        return args -> {
            Classroom androidClass = new Classroom(
                    "Android 101",
                    1L,
                    List.of(1L, 2L)
            );

            repo.saveAll(
                    List.of(androidClass)
            );
        };
    }
}
