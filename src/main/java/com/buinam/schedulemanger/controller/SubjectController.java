package com.buinam.schedulemanger.controller;

import com.buinam.schedulemanger.model.Student;
import com.buinam.schedulemanger.model.Subject;
import com.buinam.schedulemanger.model.Teacher;
import com.buinam.schedulemanger.repository.StudentRepository;
import com.buinam.schedulemanger.repository.SubjectRepository;
import com.buinam.schedulemanger.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "api/v1/subject")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectRepository subjectRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;


    @GetMapping
    List<Subject> getSubjects() {
        return subjectRepository.findAll();
    }

    @PostMapping
    Subject createSubject(@RequestBody Subject subject) {
        return subjectRepository.save(subject);
    }

    @PutMapping("/{subjectId}/students/{studentId}")
    Subject enrollStudentToSubject(@PathVariable Long subjectId, @PathVariable Long studentId) {
        Subject subject = subjectRepository.findById(subjectId).orElseThrow(() -> new IllegalArgumentException("Subject not found"));
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new IllegalArgumentException("Student not found"));
        System.out.println(subject.getName());
        System.out.println(student.getName());
        subject.enrollStudent(student);

        return subjectRepository.save(subject);
    }

    @PutMapping("/{subjectId}/teacher/{teacherId}")
    Subject assignTeacherToSubject(@PathVariable Long subjectId, @PathVariable Long teacherId) {
        Subject subject = subjectRepository.findById(subjectId).orElseThrow(() -> new IllegalArgumentException("Subject not found"));
        Teacher teacher = teacherRepository.findById(teacherId).orElseThrow(() -> new IllegalArgumentException("teacher not found"));

        subject.assignTeacher(teacher);

        return subjectRepository.save(subject);
    }
}
