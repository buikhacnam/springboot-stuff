package com.buinam.schedulemanger.service;

import com.buinam.schedulemanger.dto.LazyLoadDTO;
import com.buinam.schedulemanger.model.ScheduleCategories;

import java.util.List;

public interface ScheduleCategoriesService {
    ScheduleCategories createOrUpdateScheduleCategories(ScheduleCategories scheduleCategories);

    LazyLoadDTO search(String textSeach);

    ScheduleCategories findCategoryById(Long id);
}
