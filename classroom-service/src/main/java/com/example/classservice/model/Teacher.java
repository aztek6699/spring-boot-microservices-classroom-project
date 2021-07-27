package com.example.classservice.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "teacher")
public class Teacher {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Subject is mandatory")
    private String subject;

    @NotBlank(message = "Email is mandatory")
    private String email;

    @NotNull(message = "Date of birth is mandatory")
    private LocalDate dob;

    public Teacher() {

    }

    public Teacher(Long id, String name, String subject, String email, LocalDate dob) {
        this.id = id;
        this.name = name;
        this.subject = subject;
        this.email = email;
        this.dob = dob;
    }

    public Teacher(String name, String subject, String email, LocalDate dob) {
        this.name = name;
        this.subject = subject;
        this.email = email;
        this.dob = dob;
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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }
}
