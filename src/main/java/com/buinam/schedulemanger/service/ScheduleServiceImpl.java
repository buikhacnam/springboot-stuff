package com.buinam.schedulemanger.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.buinam.schedulemanger.dto.ScheduleDTO;
import com.buinam.schedulemanger.mapper.GenerateMapper;
import com.buinam.schedulemanger.model.MapSchedule;
import com.buinam.schedulemanger.model.Schedule;
import com.buinam.schedulemanger.repository.MapScheduleRepository;
import com.buinam.schedulemanger.repository.ScheduleRepository;
import com.buinam.schedulemanger.utils.CommonUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
// @Transactional
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private MapScheduleRepository mapScheduleRepository;

    @Autowired
    private GenerateMapper mapper;

    public ScheduleDTO createOrUpdateSchedule(ScheduleDTO scheduleDTO) {

        Schedule scheduleToSave = null;

        Optional<Schedule> schedule = scheduleRepository.findById(CommonUtils.safeToLong(scheduleDTO.getId()));

        if (!schedule.isPresent()) {
            scheduleToSave = mapper.mapScheduleFromDTO(scheduleDTO);

            scheduleToSave.setCreateDate(LocalDateTime.now());
            scheduleToSave.setUpdateDate(LocalDateTime.now());
            String userName = scheduleDTO.getUpdateUser();
            scheduleToSave.setCreateUser(userName);
            scheduleToSave.setUpdateUser(userName);

        }
        // save schedule data to schedule table
        Schedule scheduleSending = scheduleRepository.save(scheduleToSave);

        // if there is an array of categories being sent, then save them to the
        // map_schedule table
        if (!ObjectUtils.isEmpty(scheduleDTO.getCategories())) {
            // loop over the categories id list
            // save each category(id) to the categoryId field of map_schedule table
            // save the schedule id to the scheduleId field of map_schedule table
            List<MapSchedule> mapScheduleList = new ArrayList<MapSchedule>();
            scheduleDTO.getCategories().forEach(categoryId -> {
                Long scheduleId = scheduleSending.getId();
                mapScheduleList.add(new MapSchedule(null, categoryId, scheduleId));
            });

            //loop through the list of map_schedule and add userName to each map_schedule
            mapScheduleList.forEach(mapSchedule -> {
                mapSchedule.setUpdateUser(scheduleSending.getUpdateUser());
                mapSchedule.setCreateUser(scheduleSending.getUpdateUser());
            });

            mapScheduleRepository.saveAll(mapScheduleList);
        }

        // convert data to DTO and add categoriest list
        ScheduleDTO scheduleDTOResult = mapper.mapScheduleFromEntity(scheduleToSave);
        scheduleDTOResult.setCategories(scheduleDTO.getCategories());
        return scheduleDTOResult;

    }
}
