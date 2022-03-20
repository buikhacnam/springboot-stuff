package com.buinam.schedulemanger.service;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.transaction.Transactional;

import com.buinam.schedulemanger.dto.ScheduleDTO;
import com.buinam.schedulemanger.model.Schedule;

import com.buinam.schedulemanger.repository.ScheduleRepository;
import com.buinam.schedulemanger.utils.CommonUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
// @Transactional
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;
    
    public Schedule createOrUpdateSchedule(Schedule scheduleDTO) {

        Optional<Schedule> schedule = scheduleRepository.findById(CommonUtils.safeToLong(scheduleDTO.getId()));

        if(!schedule.isPresent()) {
            scheduleDTO.setCreateDate(LocalDateTime.now());
            scheduleDTO.setUpdateDate(LocalDateTime.now());
          
        }
        scheduleRepository.save(scheduleDTO);
        return scheduleDTO;
    }
}
