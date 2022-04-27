package com.buinam.schedulemanger.controller;

import com.buinam.schedulemanger.model.Student;
import com.buinam.schedulemanger.model.Subject;
import com.buinam.schedulemanger.model.Teacher;
import com.buinam.schedulemanger.repository.SubjectRepository;
import com.buinam.schedulemanger.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/teacher")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherRepository teacherRepository;
    private final SubjectRepository subjectRepository;

    @GetMapping
    List<Teacher> getTeachers() {
        return teacherRepository.findAll();
    }

    @PostMapping
    Teacher createTeacher(@RequestBody Teacher teacher) {
        return teacherRepository.save(teacher);
    }


}
