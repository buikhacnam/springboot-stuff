package com.buinam.schedulemanger.controller;

import com.buinam.schedulemanger.dto.TeacherDTO;
import com.buinam.schedulemanger.mapper.GenerateMapper;
import com.buinam.schedulemanger.model.Student;
import com.buinam.schedulemanger.model.Subject;
import com.buinam.schedulemanger.model.Teacher;
import com.buinam.schedulemanger.repository.SubjectRepository;
import com.buinam.schedulemanger.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "api/v1/teacher")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherRepository teacherRepository;
    private final SubjectRepository subjectRepository;
    private final GenerateMapper mapper;

    @GetMapping("/all1")
    List<Teacher> getTeachers() {
        return teacherRepository.findAll();
    }

    @PostMapping
    Teacher createTeacher(@RequestBody Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    @GetMapping("/all")
    List<TeacherDTO> getTeachersFull() {
        List<Teacher> teachers = teacherRepository.findAll();
        List<TeacherDTO> teacherDTO = teachers.stream().map(teacher -> {
            System.out.println("TEACHER: "+teacher.getSubjects());
//            return mapper.mapTeacherFromEntity(teacher);
            TeacherDTO dto = new TeacherDTO();
            BeanUtils.copyProperties(teacher, dto);
            return dto;
        }).collect(Collectors.toList());

        return teacherDTO;
    }


    @GetMapping("/{id}")
    TeacherDTO getTeacher(@PathVariable Long id) {
        Teacher teacher = teacherRepository.findById(id).orElse(null);
        TeacherDTO teacherDTO = mapper.mapTeacherFromEntity(teacher);
        return teacherDTO;
    }


}
