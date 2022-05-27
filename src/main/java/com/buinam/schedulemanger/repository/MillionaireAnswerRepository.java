package com.buinam.schedulemanger.repository;

import com.buinam.schedulemanger.model.MillionaireAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MillionaireAnswerRepository extends JpaRepository<MillionaireAnswer, Long> {
    List<MillionaireAnswer> findByQuestionId(Long id);

    @Modifying
    @Query(value = "DELETE FROM millionaire_answer m WHERE m.question_id = ?1", nativeQuery = true)
    void deleteAllByQuestionId(Long id);
}

