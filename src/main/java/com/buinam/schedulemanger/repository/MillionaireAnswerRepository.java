package com.buinam.schedulemanger.repository;

import com.buinam.schedulemanger.model.MillionaireAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MillionaireAnswerRepository extends JpaRepository<MillionaireAnswer, Long> {
    List<MillionaireAnswer> findByQuestionId(Long id);

}

