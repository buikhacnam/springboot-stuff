package com.buinam.schedulemanger.repository;

import com.buinam.schedulemanger.model.MapSchedule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MapScheduleRepository extends JpaRepository<MapSchedule, Long> {

    List<MapSchedule> findByScheduleId(Long id);

    @Query(value="SELECT * FROM map_schedule m WHERE m.category_id IN ?1", nativeQuery = true)
    List<MapSchedule> findAllByCategoryId(List<Long> ids);

    void deleteAllByScheduleId(Long id);

}
    
