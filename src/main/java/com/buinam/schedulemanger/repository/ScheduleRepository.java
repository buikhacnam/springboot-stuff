package com.buinam.schedulemanger.repository;

import java.util.List;

import com.buinam.schedulemanger.model.Schedule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    @Query(value = "SELECT * FROM schedule.schedule WHERE MATCH (name, description, location) AGAINST (?1)", nativeQuery = true)
    public List<Schedule> searchTextSchedule(String searchText);

    public Long countAllByNameContaining(String name);

    public List<Schedule> findAllByNameContaining(String name);

    // public List<Schedule> findAllByNameContainingAndDescriptionContainingAndLocationContaining(String name, String description, String location);

}
