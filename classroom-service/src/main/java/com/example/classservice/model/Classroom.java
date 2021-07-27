package com.example.classservice.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "classroom")
public class Classroom {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private Long teacher;
    @ElementCollection
    private List<Long> students;

    public Classroom() {

    }

    public Classroom(Long id, String name, Long teacher, List<Long> students) {
        this.id = id;
        this.name = name;
        this.teacher = teacher;
        this.students = students;
    }

    public Classroom(String name, Long teacher, List<Long> students) {
        this.name = name;
        this.teacher = teacher;
        this.students = students;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getTeacher() {
        return teacher;
    }

    public void setTeacher(Long teacher) {
        this.teacher = teacher;
    }

    public List<Long> getStudents() {
        return students;
    }

    public void setStudents(List<Long> students) {
        this.students = students;
    }
}
