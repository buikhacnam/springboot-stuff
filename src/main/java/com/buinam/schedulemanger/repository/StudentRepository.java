package com.buinam.schedulemanger.repository;

import com.buinam.schedulemanger.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
