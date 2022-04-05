package com.buinam.schedulemanger.controller;

import java.util.ArrayList;
import java.util.List;

import com.buinam.schedulemanger.dto.LazyLoadDTO;
import com.buinam.schedulemanger.dto.ScheduleDTO;
import com.buinam.schedulemanger.dto.ScheduleDetailDTO;
import com.buinam.schedulemanger.model.Schedule;
import com.buinam.schedulemanger.service.ScheduleService;
import com.buinam.schedulemanger.utils.CommonResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/schedule")
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;


    @GetMapping("/search")
    public ResponseEntity<CommonResponse> searchSchedule(
        @RequestParam(required = false) String pageSize,
        @RequestParam(required = false) String pageNumber,
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String fromDate,
        @RequestParam(required = false) String toDate,
        @RequestParam(required = false) String description,
        @RequestParam(required = false) String location,
        @RequestParam(required = false) String textSearch,
        @RequestParam(required = false) String orderBy,
        @RequestParam(required = false) String orderType
    ) {
        try {
            LazyLoadDTO schedules = scheduleService.searchSchedule(pageSize, pageNumber, name, fromDate, toDate, description, location, textSearch, orderBy, orderType);
            return new ResponseEntity<>(
                new CommonResponse(
                    "Search schedule successfully",
                    true,
                    schedules,
                    HttpStatus.OK.value()
                ), 
                HttpStatus.OK)
            ;
        } catch(Exception e) {
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

    @GetMapping("details/{id}")
    public ResponseEntity<CommonResponse> getScheduleDetail(@PathVariable(name = "id") Long id) {
        try {
            ScheduleDetailDTO scheduleDetailDTO = scheduleService.getScheduleDetail(id);
            return new ResponseEntity<>(
                    new CommonResponse(
                            "get schedule details successfully",
                            true,
                            scheduleDetailDTO,
                            HttpStatus.OK.value()
                    ),
                    HttpStatus.OK);
        } catch(Exception e) {
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

    @GetMapping("/calendar")
    public ResponseEntity<CommonResponse> getScheduleBetweenDates(
        @RequestParam(required = false) String fromDate,
        @RequestParam(required = false) String toDate
    ) {
        String userName = "buinam";
        try {
            List<ScheduleDTO> scheduleDTOList = scheduleService.getScheduleBetweenDates(userName, fromDate, toDate);
            return new ResponseEntity<>(
                    new CommonResponse(
                            "get schedule between dates successfully",
                            true,
                            scheduleDTOList,
                            HttpStatus.OK.value()
                    ),
                    HttpStatus.OK);
        } catch(Exception e) {
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







//@GetMapping("/search/text")
//    public List<Schedule> searchTextSchedule(
//        @RequestParam(required = false) String pageSize,
//        @RequestParam(required = false) String pageNumber,
//        @RequestParam(required = false) String name,
//        @RequestParam(required = false) String fromDate,
//        @RequestParam(required = false) String toDate,
//        @RequestParam(required = false) String description,
//        @RequestParam(required = false) String location,
//        @RequestParam(required = false) String searchText
//    ) {
//        try {
//            System.out.println("seeeeeeeeeeeeeeee" + searchText);
//            List<Schedule> schedules = scheduleService.searchTextSchedule(pageSize, pageNumber ,name, fromDate, toDate, description, location, searchText);
//            return schedules;
//        } catch(Exception e) {
//            System.out.print(e.getMessage() );
//            return null;
//        }
//    }