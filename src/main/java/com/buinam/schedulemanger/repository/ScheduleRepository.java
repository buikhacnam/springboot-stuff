package com.buinam.schedulemanger.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.buinam.schedulemanger.model.Schedule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    public Long countAllByNameContaining(String name);

    public List<Schedule> findAllByNameContaining(String name);

    @Query(value = "SELECT * FROM schedule s WHERE s.create_user = ?1 AND s.start_date_time >= ?2 AND s.end_date_time <= ?3", nativeQuery = true)
    List<Schedule> findByUserNameAndBetweenDates(String userName, String fromDate, String toDate);

    @Query(value = "SELECT * FROM schedule s WHERE s.create_user = ?1 AND s.start_date_time >= ?2 AND s.end_date_time <= ?3 AND s.id IN ?4", nativeQuery = true)
    List<Schedule> findByUserNameAndBetweenDatesAndIds(String userName, String fromDate, String toDate, List<Long> scheduleIds);

    // public List<Schedule> findAllByNameContainingAndDescriptionContainingAndLocationContaining(String name, String description, String location);

}
