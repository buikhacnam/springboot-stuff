package com.buinam.schedulemanger.controller;

import com.buinam.schedulemanger.dto.ScheduleDTO;
import com.buinam.schedulemanger.model.Schedule;
import com.buinam.schedulemanger.service.ScheduleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/schedule")
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;

    @PostMapping("/save")
    public Schedule createSchedule(@RequestBody Schedule schedule) {

        Schedule scheduleDTOResult = scheduleService.createOrUpdateSchedule(schedule);
        return scheduleDTOResult;
    }
}
