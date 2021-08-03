package com.example.classservice.service;

import com.example.classservice.model.GenericResponse;
import com.example.classservice.model.Student;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.List;

@Service
public class StudentService {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Value("${student.service.url}")
    private String studentBaseURL;

    @HystrixCommand(fallbackMethod = "getStudentsFallback")
    public GenericResponse getStudents(List<Long> list) {
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

    private GenericResponse getStudentsFallback(List<Long> list) {
        Student fallbackStudent = new Student(0L, "N/A", "N/A", LocalDate.now());
        return new GenericResponse(false, "Student service under heavy load, try again later", 500, List.of(fallbackStudent));
    }
}
