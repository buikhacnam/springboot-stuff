package com.buinam.schedulemanger.repository;

import com.buinam.schedulemanger.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
}
