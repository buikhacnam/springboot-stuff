package com.buinam.schedulemanger.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.buinam.schedulemanger.model.Schedule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    public Long countAllByNameContaining(String name);

    public List<Schedule> findAllByNameContaining(String name);

    @Query(value = "SELECT * FROM schedule s WHERE s.create_user = ?1 AND (" +
            "s.start_date_time >= DATE_SUB(?2, INTERVAL 31 DAY) AND s.end_date_time <= DATE_ADD(?3, INTERVAL 31 DAY)" +
            ")", nativeQuery = true)
    List<Schedule> findByUserNameAndBetweenDates(String userName, String fromDate, String toDate);

    @Query(value = "SELECT * FROM schedule s WHERE s.create_user = ?1 AND ss.start_date_time >= DATE_SUB(?2, INTERVAL 31 DAY) AND s.end_date_time <= DATE_ADD(?3, INTERVAL 31 DAY) AND s.id IN ?4", nativeQuery = true)
    List<Schedule> findByUserNameAndBetweenDatesAndIds(String userName, String fromDate, String toDate, List<Long> scheduleIds);

    @Query(value = "SELECT * FROM schedule s WHERE s.id = ?1 AND s.create_user = ?2", nativeQuery = true)
    Optional<Schedule> findByIdAndUserName(Long safeToLong, String userName);

}

