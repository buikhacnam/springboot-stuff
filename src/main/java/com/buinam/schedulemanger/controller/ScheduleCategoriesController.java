package com.buinam.schedulemanger.controller;

import com.buinam.schedulemanger.model.ScheduleCategories;
import com.buinam.schedulemanger.service.ScheduleCategoriesService;

import org.springframework.web.bind.annotation.PostMapping;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping(path = "api/v1/schedule-categories")
public class ScheduleCategoriesController {
    @Autowired
    private ScheduleCategoriesService scheduleCategoriesService;

    @PostMapping("/save")
    public ScheduleCategories createScheduleCategories(@RequestBody ScheduleCategories scheduleCategories) {

        ScheduleCategories scheduleCategoriesResult = scheduleCategoriesService.createOrUpdateScheduleCategories(scheduleCategories);
        return scheduleCategoriesResult;
    }
}
