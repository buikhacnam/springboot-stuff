package com.buinam.schedulemanger.mapper;

import com.buinam.schedulemanger.dto.*;
import com.buinam.schedulemanger.model.Schedule;

import com.buinam.schedulemanger.model.Student;
import com.buinam.schedulemanger.model.Teacher;
import com.buinam.schedulemanger.sandbox.Car;
import com.buinam.schedulemanger.sandbox.Motorbike;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface GenerateMapper {
    Schedule mapScheduleFromDTO (ScheduleDTO scheduleDTO);
    ScheduleDTO mapScheduleFromEntity (Schedule schedule);
    ScheduleDetailDTO mapScheduleDetailFromEntity (Schedule schedule);

    StudentDTO mapStudentFromEntity (Student student);
    TeacherDTO mapTeacherFromEntity (Teacher teacher);

    Motorbike mapMotorbikeFromCar (Car car);
    Car mapCarFromMotorbike (Motorbike motorbike);
}
