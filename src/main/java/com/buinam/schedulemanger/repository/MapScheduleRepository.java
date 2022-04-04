package com.buinam.schedulemanger.repository;

import com.buinam.schedulemanger.model.MapSchedule;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MapScheduleRepository extends JpaRepository<MapSchedule, Long> {
    List<MapSchedule> findAllById(Long id);

    List<MapSchedule> findByScheduleId(Long id);

    void deleteAllByScheduleId(Long id);
}
    
