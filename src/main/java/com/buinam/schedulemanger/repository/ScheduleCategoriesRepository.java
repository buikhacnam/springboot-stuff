package com.buinam.schedulemanger.repository;

import com.buinam.schedulemanger.model.ScheduleCategories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleCategoriesRepository extends JpaRepository<ScheduleCategories, Long> {

}
    

