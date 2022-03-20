package com.buinam.schedulemanger.service;

import com.buinam.schedulemanger.dto.ScheduleDTO;
import com.buinam.schedulemanger.model.Schedule;

public interface ScheduleService {
    Schedule createOrUpdateSchedule(Schedule schedule);
}
