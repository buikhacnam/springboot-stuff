package com.buinam.schedulemanger.mapper;

import com.buinam.schedulemanger.dto.ScheduleDTO;
import com.buinam.schedulemanger.dto.ScheduleDetailDTO;
import com.buinam.schedulemanger.dto.StudentDTO;
import com.buinam.schedulemanger.dto.StudentEnrolledDTO;
import com.buinam.schedulemanger.model.Schedule;

import com.buinam.schedulemanger.model.Student;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface GenerateMapper {
    Schedule mapScheduleFromDTO (ScheduleDTO scheduleDTO);
    ScheduleDTO mapScheduleFromEntity (Schedule schedule);
    ScheduleDetailDTO mapScheduleDetailFromEntity (Schedule schedule);

    StudentDTO mapStudentFromEntity (Student student);
}
