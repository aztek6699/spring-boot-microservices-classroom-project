package com.example.classservice.model;

import java.util.List;

public class ClassroomDto {

    private Long id;

    private String name;
    private Teacher teacher;
    private List<Student> students;

    public ClassroomDto(Long id, String name, Teacher teacher, List<Student> students) {
        this.id = id;
        this.name = name;
        this.teacher = teacher;
        this.students = students;
    }

    public ClassroomDto(String name, Teacher teacher, List<Student> students) {
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

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
