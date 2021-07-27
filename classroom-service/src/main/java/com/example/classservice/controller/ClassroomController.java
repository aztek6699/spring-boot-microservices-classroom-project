package com.example.classservice.controller;

import com.example.classservice.model.GenericResponse;
import com.example.classservice.service.ClassroomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/classroom")
@Slf4j
public class ClassroomController {

    private final ClassroomService service;

    @Autowired
    public ClassroomController(ClassroomService service) {
        this.service = service;
    }

    @RequestMapping("/{id}")
    public ResponseEntity<GenericResponse> getClassroomById(@PathVariable Long id) {
        return service.getClassById(id);
    }

}
