package com.buinam.schedulemanger.controller;

import com.buinam.schedulemanger.dto.StudentDTO;
import com.buinam.schedulemanger.model.Student;
import com.buinam.schedulemanger.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
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

    @GetMapping("/all")
    List<StudentDTO> getStudentsWithSubjects() {
        List<Object> listParam = new ArrayList<>();
        StringBuilder strQuery = new StringBuilder();

        strQuery.append("SELECT * FROM student_enrolled s ");

//        Query query = em.createNativeQuery(strQuery.toString(), "student_enrolled_kkk");
        
        //or using @NamedNativeQuery
        Query query = em.createNamedQuery("getAllEnrolledStudent");
        
        
        
        return query.getResultList();


//        return null;

    }


    @GetMapping("/result-one")
    List<Object> getSomeData() {
        Query query = em.createNativeQuery("SELECT * FROM result_set_demo_one", "get_result_one");
        return query.getResultList();
    }


    @GetMapping("/result-two")
    List<Object> getSomeData2() {
        Query query = em.createNativeQuery("SELECT * FROM result_set_demo_two", "get_result_two");
        return query.getResultList();
    }


    @PostMapping
    Student createStudent(@RequestBody Student student) {
        return studentRepository.save(student);
    }
}