package com.buinam.schedulemanger.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
// @JsonIgnoreProperties(ignoreUnknown = true) why need this?

// the class implements Serializable ????
public class ScheduleDTO {
    private Long id;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private String name;
    private String description;
    private String location;
    private String participator;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private List<Long> categories;
}
