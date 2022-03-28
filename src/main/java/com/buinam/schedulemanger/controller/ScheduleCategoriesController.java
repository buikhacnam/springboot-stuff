package com.buinam.schedulemanger.controller;

import com.buinam.schedulemanger.dto.LazyLoadDTO;
import com.buinam.schedulemanger.model.ScheduleCategories;
import com.buinam.schedulemanger.service.ScheduleCategoriesService;

import com.buinam.schedulemanger.utils.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@RestController
@RequestMapping(path = "api/v1/schedule-categories")
public class ScheduleCategoriesController {
    @Autowired
    private ScheduleCategoriesService scheduleCategoriesService;

    @GetMapping("/search")
    public ResponseEntity<CommonResponse> search(@RequestParam(required = false) String textSearch) {
        LazyLoadDTO scheduleCategoriesList = scheduleCategoriesService.search(textSearch);
        try {
            return new ResponseEntity<>(
                    new CommonResponse(
                            "Search schedule successfully",
                            true,
                            scheduleCategoriesList,
                            HttpStatus.OK.value()
                    ),
                    HttpStatus.OK
            );
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

    @PostMapping("/save")
    public ResponseEntity<CommonResponse> createScheduleCategories(@RequestBody ScheduleCategories scheduleCategories) {
        ScheduleCategories scheduleCategoriesResult = scheduleCategoriesService.createOrUpdateScheduleCategories(scheduleCategories);
        try {
            return new ResponseEntity<>(
                    new CommonResponse(
                            "Create schedule successfully",
                            true,
                            scheduleCategoriesResult,
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
