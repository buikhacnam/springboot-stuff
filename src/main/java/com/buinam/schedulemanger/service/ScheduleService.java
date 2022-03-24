package com.buinam.schedulemanger.service;

import java.util.List;

import com.buinam.schedulemanger.dto.LazyLoadDTO;
import com.buinam.schedulemanger.dto.ScheduleDTO;
import com.buinam.schedulemanger.model.Schedule;

public interface ScheduleService {
    ScheduleDTO createOrUpdateSchedule(ScheduleDTO scheduleDTO);

    LazyLoadDTO searchSchedule(String pageSize, String pageNumber, String name, String fromDate, String toDate,
            String description, String location);

    List<Schedule> searchTextSchedule(String pageSize, String pageNumber, String name, String fromDate, String toDate,
            String description, String location, String searchText);
}
