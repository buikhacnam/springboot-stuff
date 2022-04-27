package com.buinam.schedulemanger.controller;

import com.buinam.schedulemanger.model.Student;
import com.buinam.schedulemanger.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import java.util.List;

@RestController
@RequestMapping("api/v1/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentRepository studentRepository;

    private final EntityManager em;

    @GetMapping
    List<Student> getStudents() {
        return studentRepository.findAll();

    }


    @PostMapping
    Student createStudent(@RequestBody Student student) {
        return studentRepository.save(student);
    }
}