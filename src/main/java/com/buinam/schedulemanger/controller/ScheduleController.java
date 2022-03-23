package com.buinam.schedulemanger.controller;

import java.util.ArrayList;

import com.buinam.schedulemanger.dto.ScheduleDTO;
import com.buinam.schedulemanger.model.Schedule;
import com.buinam.schedulemanger.service.ScheduleService;
import com.buinam.schedulemanger.utils.CommonResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<CommonResponse> createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        try {
            // get userName from principal:
            String userName = "buinam";
            scheduleDTO.setCreateUser(userName);
            scheduleDTO.setUpdateUser(userName);

            // if there's no info about the list if categories, then create an empty list
            if (CollectionUtils.isEmpty(scheduleDTO.getCategories())) {
                scheduleDTO.setCategories(new ArrayList<>());
            }

            ScheduleDTO scheduleDTOResult = scheduleService.createOrUpdateSchedule(scheduleDTO);
            return new ResponseEntity<>(
                new CommonResponse(
                    "Create schedule successfully",
                    true,
                    scheduleDTOResult,
                    HttpStatus.OK.value()
                ), 
                HttpStatus.OK)
            ;
        } catch (Exception e) {
            return new ResponseEntity<>(
                new CommonResponse(
                    e.getMessage(), 
                    true, 
                    null, 
                    HttpStatus.BAD_REQUEST.value()
                ),
                HttpStatus.BAD_REQUEST
            );
        }
    }
}
