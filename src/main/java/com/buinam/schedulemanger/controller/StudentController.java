package com.buinam.schedulemanger.controller;

import com.buinam.schedulemanger.dto.StudentDTO;
import com.buinam.schedulemanger.dto.StudentEnrolledDTO;
import com.buinam.schedulemanger.mapper.GenerateMapper;
import com.buinam.schedulemanger.model.Student;
import com.buinam.schedulemanger.model.Subject;
import com.buinam.schedulemanger.repository.StudentRepository;
import com.buinam.schedulemanger.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentRepository studentRepository;

    private final SubjectRepository subjectRepository;

    private final EntityManager em;

    private final GenerateMapper mapper;

    @GetMapping
    List<Student> getStudents() {
        return studentRepository.findAll();

    }

    @GetMapping("/all")
    List<StudentDTO> getStudentsWithSubjects() {

        List<Student> students = studentRepository.findAll();
        List<StudentDTO> studentEnrolledDTOList = new ArrayList<>();
        students.forEach(student -> {
            StudentDTO studentEnrolledDTO = mapper.mapStudentFromEntity(student);
            studentEnrolledDTOList.add(studentEnrolledDTO);
        });
       studentEnrolledDTOList.stream().map(student -> {
            List<Object> listParam = new ArrayList<>();
            listParam.add(student.getId());
            Query query = em.createNativeQuery("SELECT * FROM student_enrolled s WHERE s.student_id = ?", "student_enrolled_kkk");
            query.setParameter(1, student.getId());
            List<StudentEnrolledDTO> studentEnrolledList = query.getResultList();
            studentEnrolledList.forEach(s -> {
                Optional<Subject> subject = subjectRepository.findById(s.getSubject_id());
                if(subject.isPresent()) {
                    System.out.println(subject.get());
                    student.getSubjectNha().add(subject.get());
                }
            });
            return student;
        }).collect(Collectors.toList());

        return studentEnrolledDTOList;
//        Query query = em.createNativeQuery(strQuery.toString(), "student_enrolled_kkk");
        //or using @NamedNativeQuery
//        Query query = em.createNamedQuery("getAllEnrolledStudent");

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