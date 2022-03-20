package com.buinam.schedulemanger.repository;

import com.buinam.schedulemanger.model.Schedule;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    
}
