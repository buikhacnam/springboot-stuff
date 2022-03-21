package com.buinam.schedulemanger.service;

import com.buinam.schedulemanger.dto.ScheduleDTO;

public interface ScheduleService {
    ScheduleDTO createOrUpdateSchedule(ScheduleDTO scheduleDTO);
}
