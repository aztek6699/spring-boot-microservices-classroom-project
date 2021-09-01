package com.example.classservice.service;

import com.example.classservice.model.GenericResponse;
import com.example.classservice.model.ServiceUrl;
import com.example.classservice.model.Teacher;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.List;

@Service
public class TeacherService {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private ServiceUrl serviceUrl;

//    @Value("${teacher.service.url}")
//    private String teacherBaseURL;

    @HystrixCommand(fallbackMethod = "getTeacherFallback")
    public GenericResponse getTeacher(Long teacherId) {
        String teacherUrl = serviceUrl.getTeacher() + "" + teacherId;
        return webClientBuilder.build()
                .get()
                .uri(teacherUrl)
                .retrieve()
                .bodyToMono(GenericResponse.class)
                .block();
    }

    private GenericResponse getTeacherFallback(Long teacherId) {
        Teacher fallbackTeacher = new Teacher(0L, "N/A", "N/A", "N/A", LocalDate.now());
        return new GenericResponse(false, "Teacher service under heavy load, try again later", 500, List.of(fallbackTeacher));
    }
}
