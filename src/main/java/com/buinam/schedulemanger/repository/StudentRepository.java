package com.buinam.schedulemanger.repository;

import com.buinam.schedulemanger.dto.StudentDTO;
import com.buinam.schedulemanger.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

}
