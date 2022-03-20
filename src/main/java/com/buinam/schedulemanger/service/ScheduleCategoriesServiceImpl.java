package com.buinam.schedulemanger.service;

import java.time.LocalDateTime;
import java.util.Optional;

import com.buinam.schedulemanger.model.ScheduleCategories;
import com.buinam.schedulemanger.repository.ScheduleCategoriesRepository;
import com.buinam.schedulemanger.utils.CommonUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleCategoriesServiceImpl implements ScheduleCategoriesService {
    
    @Autowired
    private ScheduleCategoriesRepository scheduleCategoriesRepository;
    
    @Override
    public ScheduleCategories createOrUpdateScheduleCategories(ScheduleCategories scheduleCategories) {
        Optional<ScheduleCategories> scheduleCategoriesResult = scheduleCategoriesRepository.findById(CommonUtils.safeToLong(scheduleCategories.getId()));

        if(!scheduleCategoriesResult.isPresent()) {
            scheduleCategories.setCreateDate(LocalDateTime.now());
            scheduleCategories.setUpdateDate(LocalDateTime.now());
        }

        scheduleCategoriesRepository.save(scheduleCategories);
        return scheduleCategories;
    }
}
    

