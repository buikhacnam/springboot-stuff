package com.buinam.schedulemanger.controller;

import java.security.Principal;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
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
    public ResponseEntity<CommonResponse> createSchedule(Principal principal, @RequestBody ScheduleDTO scheduleDTO) {
        try {
            // get userName from principal:
            String userName = principal.getName();
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
    public ResponseEntity<CommonResponse> getScheduleDetail(Principal principal, @PathVariable(name = "id") Long id) {
        String userName = principal.getName();
        try {
            ScheduleDetailDTO scheduleDetailDTO = scheduleService.getScheduleDetail(userName,id);
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
            Principal principal,
        @RequestParam(required = true) String fromDate,
        @RequestParam(required = true) String toDate,
        @RequestParam(required = false) List<Long> categories
    ) {
        String userName = principal.getName();
        try {
            List<ScheduleDetailDTO> scheduleDTOList = scheduleService.getScheduleBetweenDates(userName, fromDate, toDate, categories);
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
