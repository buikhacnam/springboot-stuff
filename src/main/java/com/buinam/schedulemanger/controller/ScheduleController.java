package com.buinam.schedulemanger.controller;

import java.util.ArrayList;

import com.buinam.schedulemanger.dto.ScheduleDTO;
import com.buinam.schedulemanger.model.Schedule;
import com.buinam.schedulemanger.service.ScheduleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
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
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {

        //get userName from principal:
        String userName = "buinam";
        scheduleDTO.setCreateUser(userName);
        scheduleDTO.setUpdateUser(userName);

        // if there's no info about the list if categories, then create an empty list
        if (CollectionUtils.isEmpty(scheduleDTO.getCategories())) {
            scheduleDTO.setCategories(new ArrayList<>());
        }
        
        ScheduleDTO scheduleDTOResult = scheduleService.createOrUpdateSchedule(scheduleDTO);
        return scheduleDTOResult;
    }
}
