package com.buinam.schedulemanger.dto;

import com.buinam.schedulemanger.dto.StudentEnrolledDTO;
import com.buinam.schedulemanger.model.Subject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor

public class StudentDTO {

    private Long id;
    private String name;
    private Set<Subject> subjects = new HashSet<>();
    private Set<Subject> subjectNha = new HashSet<>();

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

    public Set<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(Set<Subject> subjects) {
        this.subjects = subjects;
    }

    public Set<Subject> getSubjectNha() {
        return subjectNha;
    }

    public void setSubjectNha(Set<Subject> subjectNha) {
        this.subjectNha = subjectNha;
    }
}
