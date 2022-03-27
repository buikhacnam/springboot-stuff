package com.buinam.schedulemanger.dto;

import com.buinam.schedulemanger.model.ScheduleCategories;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class ScheduleDetailDTO {
    private Long id;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private String createUser;
    private String updateUser;
    private String name;
    private String description;
    private String location;
    private String participator;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private List<ScheduleCategories> categories;
}
